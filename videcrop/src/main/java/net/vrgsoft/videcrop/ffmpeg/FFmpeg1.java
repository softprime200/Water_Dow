package net.vrgsoft.videcrop.ffmpeg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Map;

public class FFmpeg1 implements FFbinaryInterface1 {
    private static final int VERSION = 17; // up this version when you add a new ffmpeg build
    private static final String KEY_PREF_VERSION = "ffmpeg_version";

    private final FFbinaryContextProvider1 context;

    private static final long MINIMUM_TIMEOUT = 10 * 1000;
    private long timeout = Long.MAX_VALUE;

    private static FFmpeg1 instance = null;

    private FFmpeg1(FFbinaryContextProvider1 context) {
        this.context = context;
        Log1.setDebug(Util1.isDebug(this.context.provide()));
    }

    public static FFmpeg1 getInstance(final Context context) {
        if (instance == null) {
            instance = new FFmpeg1(new FFbinaryContextProvider1() {
                @Override
                public Context provide() {
                    return context;
                }
            });
        }
        return instance;
    }

    @Override
    public boolean isSupported() {
        // check if arch is supported
        CpuArch1 cpuArch = CpuArchHelper1.getCpuArch();
        if (cpuArch == CpuArch1.NONE) {
            Log1.e("arch not supported");
            return false;
        }

        // get ffmpeg file
        File ffmpeg = FileUtils1.getFFmpeg(context.provide());

        SharedPreferences settings = context.provide().getSharedPreferences("ffmpeg_prefs", Context.MODE_PRIVATE);
        int version = settings.getInt(KEY_PREF_VERSION, 0);

        // check if ffmpeg file exists
        if (!ffmpeg.exists() || version < VERSION) {
            String prefix = "arm/";
            if (cpuArch == CpuArch1.x86) {
                prefix = "x86/";
            }
            Log1.d("file does not exist, creating it...");

            try {
                InputStream inputStream = context.provide().getAssets().open(prefix + "ffmpeg");
                if (!FileUtils1.inputStreamToFile(inputStream, ffmpeg)) {
                    return false;
                }

                Log1.d("successfully wrote ffmpeg file!");

                settings.edit().putInt(KEY_PREF_VERSION, VERSION).apply();
            } catch (IOException e) {
                Log1.e("error while opening assets", e);
                return false;
            }
        }

        // check if ffmpeg can be executed
        if (!ffmpeg.canExecute()) {
            // try to make executable
            try {
                try {
                    Runtime.getRuntime().exec("chmod -R 777 " + ffmpeg.getAbsolutePath()).waitFor();
                } catch (InterruptedException e) {
                    Log1.e("interrupted exception", e);
                    return false;
                } catch (IOException e) {
                    Log1.e("io exception", e);
                    return false;
                }

                if (!ffmpeg.canExecute()) {
                    // our last hope!
                    if (!ffmpeg.setExecutable(true)) {
                        Log1.e("unable to make executable");
                        return false;
                    }
                }
            } catch (SecurityException e) {
                Log1.e("security exception", e);
                return false;
            }
        }

        Log1.d("ffmpeg is ready!");

        return true;
    }

    public boolean deleteFFmpegBin(){
        File file = FileUtils1.getFFmpeg(context.provide());

        if(file.exists()){
            return file.delete();
        }
        return false;
    }

    @Override
    public FFtask1 execute(Map<String, String> environvenmentVars, String[] cmd, FFcommandExecuteResponseHandler1 ffmpegExecuteResponseHandler, float duration) {
        if (cmd.length != 0) {
            String[] ffmpegBinary = new String[]{FileUtils1.getFFmpeg(context.provide()).getAbsolutePath()};
            String[] command = concatenate(ffmpegBinary, cmd);
            FFcommandExecuteAsyncTask1 task = new FFcommandExecuteAsyncTask1(command, environvenmentVars, timeout, ffmpegExecuteResponseHandler, duration);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return task;
        } else {
            throw new IllegalArgumentException("shell command cannot be empty");
        }
    }

    private static <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    @Override
    public FFtask1 execute(String[] cmd, FFcommandExecuteResponseHandler1 ffmpegExecuteResponseHandler, float duration) {
        return execute(null, cmd, ffmpegExecuteResponseHandler, duration);
    }

    @Override
    public boolean isCommandRunning(FFtask1 task) {
        return task != null && !task.isProcessCompleted();
    }

    @Override
    public boolean killRunningProcesses(FFtask1 task) {
        return task != null && task.killRunningProcess();
    }

    @Override
    public void setTimeout(long timeout) {
        if (timeout >= MINIMUM_TIMEOUT) {
            this.timeout = timeout;
        }
    }
}
