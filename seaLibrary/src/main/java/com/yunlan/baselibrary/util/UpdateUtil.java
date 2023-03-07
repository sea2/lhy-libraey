package com.yunlan.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.yunlan.baselibrary.bean.FileProvider7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Created by lhy on 2016/8/8.
 */
public class UpdateUtil {
    private static final int DOWNLOAD = 1;// 下载中
    private static final int DOWNLOAD_FINISH = 2;// 下载结束
    private static final int DOWNLOAD_FAIL = 3;// 下载失败

    public static final int DOWNLOAD_CONNECT_TIME_OUT = 30000;// 下载时链接服务器的超时时间
    public static final int DOWNLOAD_READ_TIME_OUT = 30000;// 下载时读取文件的超时时间

    private int progress;// 进度
    private Context context;
    private int count = 0; // 已读取文件大小
    private int numread;// 剩余读取文件大小
    private int fileSize;// 资源文件大小
    private boolean isInstal = false; // 下载中断标志位
    private String downloaUrl;
    private ProgressListener mProgressListener;
    private Activity activity;
    private boolean isMust = false;
    String commonPath = "";


    public interface ProgressListener {

        void onProgressView(int code, int progress);
    }

    /**
     * 构造函数
     *
     * @param context
     */
    public UpdateUtil(Context context) {
        this.context = context;
        activity = (Activity) context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            commonPath = context.getExternalCacheDir() + "/XJCache/";
        } else {
            commonPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XJCache/";
        }

    }


    public void setProgressListener(ProgressListener mProgressListener) {
        this.mProgressListener = mProgressListener;
    }


    /**
     * 更新进度条handler
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                // 下载中
                case DOWNLOAD:
                    // 更新进度
                    mProgressListener.onProgressView(0, progress);
                    break;
                // 下载结束
                case DOWNLOAD_FINISH:
                    // 关闭线程，安装apk
                    mProgressListener.onProgressView(-1, progress);
                    if (thread.isAlive()) {
                        thread.stopThread(true);
                    }
                    break;
                // 下载失败
                case DOWNLOAD_FAIL:
                    // 关闭线程，弹出提示框
                    mProgressListener.onProgressView(-2, progress);
                    if (thread.isAlive()) {
                        thread.stopThread(true);
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 选择下载提示框 SD卡存在并可用，启动下载线程
     */
    public void showChoiceDownLoadDialog(String url) {
        downloaUrl = url;
        if (context != null) {
            DownLoad();
        } else {
        }
    }


    /**
     * 必须更新提示框 SD卡存在并可用，启动下载线程
     */
    public void showMustDownLoadDialog() {

        if (context != null) {
            DownLoad();
        } else {
        }
    }


    private DownThread thread;

    /**
     * 启动下载线程
     */
    private void DownLoad() {
        thread = new DownThread();
        thread.start();
    }

    /**
     * 启动下载线程
     */
    private void stopDownLoad() {
        if (null != thread) thread.stopThread(true);
    }

    /**
     * 下载线程内部类
     *
     * @author Administrator
     */
    class DownThread extends Thread {
        private boolean _run = true;

        public void stopThread(boolean run) {
            this._run = !run;
            Thread thread = Thread.currentThread();
            if (thread != null) {
                thread = null;
            }
        }

        @Override
        public void run() {
            super.run();
            try {
                if (_run) {
                    if (context != null && (!StringUtils.isEmpty(commonPath))) {
                        isInstal = false;
                        URL url = new URL(downloaUrl);
                        if (!StringUtils.isEmpty(downloaUrl)) {
                            LogUtil.log(downloaUrl);
                        }
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 302) {//从定向下载
                            String redirectUrl = connection.getHeaderField("Location");
                            url = new URL(redirectUrl);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                        }
                        connection.setConnectTimeout(DOWNLOAD_CONNECT_TIME_OUT);
                        connection.setReadTimeout(DOWNLOAD_READ_TIME_OUT);

                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        fileSize = connection.getContentLength();
                        String fileName = downloaUrl.substring(downloaUrl.lastIndexOf("/") + 1);
                        String savaPath = commonPath;
                        File filePath = new File(savaPath);
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }
                        File file = new File(savaPath + fileName);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[2048];

                        // 读取服务端文件并保存在本地
                        while ((numread = inputStream.read(buffer)) != -1) {
                            count += numread;
                            progress = (int) (((float) count / fileSize) * 100);
                            handler.sendEmptyMessage(DOWNLOAD);// 发送下载进度
                            // 保存文件
                            fileOutputStream.write(buffer, 0, numread);
                            if (isInstal) {
                                break;
                            }
                        }
                        fileOutputStream.close();
                        inputStream.close();
                    } else {
                    }
                }
            } catch (Exception e) {
                // TODO 这个地方在网络下载断开错误时可以捕获，尤其在GPRS环境下，但是捕获出来的e=null，有待解决。
                e.printStackTrace();
                //杨佳磊 将此注释 防止在异步线程调用主线程从而报错
                //show.show("下载文件失败！");
            }

            // 判断是否下载完成，当下载的内容小于文件大小或者为0时
            if ((count < fileSize) || (count == 0)) {
                handler.sendEmptyMessage(DOWNLOAD_FAIL);
            } else {
                handler.sendEmptyMessage(DOWNLOAD_FINISH);
            }

        }
    }

    /**
     * 自动安装apk
     */
    public void installAPK() {
        if (context != null) {
            // String path = --
            // + "/download/";
            String fileName = downloaUrl.substring(downloaUrl.lastIndexOf("/") + 1);
            String savaPath = commonPath;
            File apkfile = new File(savaPath + fileName);
            if (!apkfile.exists()) {
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            FileProvider7.setIntentDataAndType(intent, "application/vnd.android.package-archive", apkfile, true);
            context.startActivity(intent);

        }
    }


    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }



}
