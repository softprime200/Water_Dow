package net.vrgsoft.videcrop.ffmpeg;

import android.os.Build;

class CpuArchHelper1 {
    private static final String X86_CPU = "x86";
    private static final String X86_64_CPU = "x86_64";
    private static final String ARM_64_CPU = "arm64-v8a";
    private static final String ARM_V7_CPU = "armeabi-v7a";

    static CpuArch1 getCpuArch() {
        Log1.d("Build.CPU_ABI : " + Build.CPU_ABI);

        switch (Build.CPU_ABI) {
            case X86_CPU:
            case X86_64_CPU:
                return CpuArch1.x86;
            case ARM_64_CPU:
            case ARM_V7_CPU:
                return CpuArch1.ARMv7;
            default:
                return CpuArch1.NONE;
        }
    }
}
