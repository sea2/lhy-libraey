package com.yunlan.baselibrary.util;

/**
 * Created by lhy on 2022/12/6.
 */

public class EventMessageDownloadApk {


  String apkPath;
  boolean isOk;
  int progress;
  int formFlag;

    public EventMessageDownloadApk(String apkPath, boolean isOk, int progress,int formFlag) {
        this.apkPath = apkPath;
        this.isOk = isOk;
        this.progress = progress;
        this.formFlag = formFlag;
    }

    public int getFormFlag() {
        return formFlag;
    }

    public void setFormFlag(int formFlag) {
        this.formFlag = formFlag;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
