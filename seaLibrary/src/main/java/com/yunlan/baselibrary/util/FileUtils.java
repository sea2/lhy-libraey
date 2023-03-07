package com.yunlan.baselibrary.util;


import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;


/**
 * 工具类
 */

public class FileUtils {



    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     */
    public static byte[] readStream(String imagepath) {
        try {
            FileInputStream fs = new FileInputStream(imagepath);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (-1 != (len = fs.read(buffer))) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            fs.close();
            return outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 以字符流读取文件
     *
     * @param path 文件路径
     * @return 字符数组
     */
    public static String getCharacterStream(String path) {
        try {
            // 创建字符流对象
            FileReader reader = new FileReader(path);
            // 创建字符串拼接
            StringBuilder builder = new StringBuilder();
            // 读取一个字符
            int read = reader.read();
            // 能读取到字符
            while (read != -1) {
                // 拼接字符串
                builder.append((char) read);
                // 读取下一个字符
                read = reader.read();
            }
            // 关闭字符流
            reader.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 获取文件大小
    public static String getFileSize(long size) {
        if (size <= 0) {
            return "0MB";
        }
        final String[] units = new String[]{"BB", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + "" + units[digitGroups];
    }


    /**
     * 将文件从assets目录，考贝到 /data/data/包名/files/ 目录中。assets 目录中的文件，会不经压缩打包至APK包中，使用时还应从apk包中导出来
     *
     * @param fileName 文件名,如aaa.txt
     */
    public static String copyAssetsFile2Phone(Activity activity, String fileName) {
        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            // getFilesDir() 获得当前APP的安装路径 /data/data/包名/files 目录
            File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + fileName);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);//如果文件不存在，FileOutputStream会自动创建文件
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();//刷新缓存区
                inputStream.close();
                fos.close();
                LogUtils.logm("直播特效＝文件复制完毕 *************************************************************");
                return file.getAbsolutePath();
            } else {
                LogUtils.logm("直播特效＝文件已经存在 *************************************************************");
                return file.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /** 获取文件后缀名
     * @param url
     * @return
     */
    public static String getFileSuffixName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            //获取最后一个.的位置
            int lastIndexOf = url.lastIndexOf(".");
            //获取文件的后缀名 .jpg
            if (lastIndexOf > 0) {
                return url.substring(lastIndexOf);
            }
        }
        return "";
    }

    public static boolean isGifFile(String url) {
        if(StringUtils.isNotEmpty(getFileSuffixName(url))&&StringUtils.isEquals(getFileSuffixName(url).toUpperCase(),".GIF")){
            return true;
        }
        return false;
    }


    public static String getFileName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            //获取最后一个.的位置
            int lastIndexOf = url.lastIndexOf("/");
            //获取文件的后缀名 .jpg
            if (lastIndexOf > 0) {
                return url.substring(lastIndexOf);
            }
        }
        return "";
    }

}
