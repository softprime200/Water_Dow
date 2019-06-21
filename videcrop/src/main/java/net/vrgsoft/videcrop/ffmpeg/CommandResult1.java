package net.vrgsoft.videcrop.ffmpeg;

class CommandResult1 {
    final String output;
    final boolean success;

    CommandResult1(boolean success, String output) {
        this.success = success;
        this.output = output;
    }

    static CommandResult1 getDummyFailureResponse() {
        return new CommandResult1(false, "");
    }

    static CommandResult1 getOutputFromProcess(Process process) {
        String output;
        if (success(process.exitValue())) {
            output = Util1.convertInputStreamToString(process.getInputStream());
        } else {
            output = Util1.convertInputStreamToString(process.getErrorStream());
        }
        return new CommandResult1(success(process.exitValue()), output);
    }

    static boolean success(Integer exitValue) {
        return exitValue != null && exitValue == 0;
    }

}