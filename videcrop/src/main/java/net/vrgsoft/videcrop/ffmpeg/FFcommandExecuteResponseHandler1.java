package net.vrgsoft.videcrop.ffmpeg;

public interface FFcommandExecuteResponseHandler1 extends ResponseHandler1 {

    /**
     * on Success
     *
     * @param message complete output of the binary command
     */
    void onSuccess(String message);

    /**
     * on Progress
     *
     * @param message current output of binary command
     */
    void onProgress(String message);

    /**
     * on Failure
     *
     * @param message complete output of the binary command
     */
    void onFailure(String message);

    void onProgressPercent(float percent);
}
