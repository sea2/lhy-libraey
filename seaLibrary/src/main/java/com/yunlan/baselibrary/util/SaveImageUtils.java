package com.yunlan.baselibrary.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.sea2.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rxhttp.RxHttp;

/**
 * Created by lhy on 2022/5/7.
 */


public class SaveImageUtils {


    /**
     * 存储到本地
     */
    public static void saveFile(String pathImg) {
        Context context=Utils.getApplication();
        if (FileUtils.isGifFile(pathImg)) {
            String fileName = context.getExternalCacheDir().getAbsolutePath() + "/down_img/" + context.getString(R.string.save_file_str) + new Date().getTime() + ".gif";
            File crashDir = new File(context.getExternalCacheDir().getAbsolutePath() + "/down_img");
            if (!crashDir.exists()) {
                crashDir.mkdir();
            }

            Disposable disposableRx = RxHttp.get(pathImg).asDownload(fileName).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> e) throws Exception {
                            saveFile2Gallery(context, new File(fileName));
                            e.onNext("str");
                        }
                    }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String str) throws Exception {
                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

        } else {
            Glide.with(context).asBitmap().load(pathImg)
                    .into(new CustomTarget<Bitmap>() {
                              @Override
                              public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                  Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
                                      @Override
                                      public void subscribe(ObservableEmitter<String> e) throws Exception {
                                          SaveImageUtils.saveFile2Gallery(context, false, null, resource);
                                          e.onNext("str");
                                      }
                                  }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                                          .subscribeOn(Schedulers.io())
                                          .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                                          .subscribe(new Consumer<String>() {
                                              @Override
                                              public void accept(String str) throws Exception {
                                                  Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                              }
                                          });
                              }

                              @Override
                              public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {
                              }
                          }
                    );
        }


    }


    //file:///storage/emulated/0/Android/data/包名/files/screenshots_videos//100000406127/pp_20220225201215.jpg
    private static void saveFile2Gallery(Context context, boolean isVideo, File videoFile, Bitmap bmp) {
        String folderName = "";
        String fileName = context.getString(R.string.save_file_str) + new Date().getTime();
        if (isVideo) {
            folderName = Environment.DIRECTORY_DCIM + "/" + context.getString(R.string.save_file_str);
        } else {
            folderName = Environment.DIRECTORY_PICTURES + "/" + context.getString(R.string.save_file_str);
        }
        Uri uriSavedFile;
        File createdFile = null;
        ContentResolver resolver = context.getContentResolver();
        ContentValues valuesFile;
        valuesFile = new ContentValues();

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.put(MediaStore.Video.Media.RELATIVE_PATH, folderName);
            valuesFile.put(MediaStore.Video.Media.TITLE, fileName);
            valuesFile.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
            valuesFile.put(MediaStore.Video.Media.MIME_TYPE, isVideo ? "video/mp4" : "image/jpeg");
            valuesFile.put(
                    MediaStore.Video.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000);

            Uri collection = isVideo ? MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) :
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            uriSavedFile = resolver.insert(collection, valuesFile);
        } else {
            File directory = Environment.getExternalStoragePublicDirectory(isVideo ? Environment.DIRECTORY_DCIM : Environment.DIRECTORY_PICTURES);
            File parent = new File(directory.getAbsolutePath() + "/" + context.getString(R.string.save_file_str));
            if (!parent.exists()) {
                parent.mkdir();
            }
            createdFile = new File(parent, fileName + (isVideo ? ".mp4" : ".jpg"));

            valuesFile.put(MediaStore.Video.Media.TITLE, fileName);
            valuesFile.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
            valuesFile.put(MediaStore.Video.Media.MIME_TYPE, isVideo ? "video/mp4" : "image/jpeg");
            valuesFile.put(
                    MediaStore.Video.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000);
            valuesFile.put(MediaStore.Video.Media.DATA, createdFile.getAbsolutePath());

            if (isVideo) {
                uriSavedFile = context.getContentResolver().insert(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        valuesFile);
            } else {
                uriSavedFile = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        valuesFile);
            }
        }

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            valuesFile.put(MediaStore.Video.Media.IS_PENDING, 1);
        }

        try {
            if (isVideo) {
                ParcelFileDescriptor pfd;
                pfd = context.getContentResolver().openFileDescriptor(uriSavedFile, "w");

                FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());
                // get the already saved video as fileinputstream
                // The Directory where your file is saved
                // File storageDir = new File(uri.getPath());
                //Directory and the name of your video file to copy
                FileInputStream in = new FileInputStream(videoFile);

                byte[] buf = new byte[8192];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                out.close();
                in.close();
                pfd.close();
            } else {
                OutputStream out = context.getContentResolver().openOutputStream(uriSavedFile);
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    bmp.recycle();
//                    out.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.clear();
            valuesFile.put(MediaStore.Video.Media.IS_PENDING, 0);
            context.getContentResolver().update(uriSavedFile, valuesFile, null, null);
        } else {
            Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            final Uri localUri = Uri.fromFile(createdFile);
            localIntent.setData(localUri);
            context.sendBroadcast(localIntent);
        }
    }

    private static void saveFile2Gallery(Context context, File file) {
        String folderName = "";
        String fileName = context.getString(R.string.save_file_str) + new Date().getTime();

        folderName = Environment.DIRECTORY_PICTURES + "/" + context.getString(R.string.save_file_str);
        Uri uriSavedFile;
        File createdFile = null;
        ContentResolver resolver = context.getContentResolver();
        ContentValues valuesFile;
        valuesFile = new ContentValues();

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.put(MediaStore.Video.Media.RELATIVE_PATH, folderName);
            valuesFile.put(MediaStore.Video.Media.TITLE, fileName);
            valuesFile.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
            valuesFile.put(MediaStore.Video.Media.MIME_TYPE, "image/gif");
            valuesFile.put(
                    MediaStore.Video.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000);

            Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            uriSavedFile = resolver.insert(collection, valuesFile);
        } else {
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File parent = new File(directory.getAbsolutePath() + "/" + context.getString(R.string.save_file_str));
            if (!parent.exists()) {
                parent.mkdir();
            }
            createdFile = new File(parent, fileName + (".gif"));

            valuesFile.put(MediaStore.Video.Media.TITLE, fileName);
            valuesFile.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
            valuesFile.put(MediaStore.Video.Media.MIME_TYPE, "image/gif");
            valuesFile.put(
                    MediaStore.Video.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000);
            valuesFile.put(MediaStore.Video.Media.DATA, createdFile.getAbsolutePath());


            uriSavedFile = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    valuesFile);
        }

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
            valuesFile.put(MediaStore.Video.Media.IS_PENDING, 1);
        }

        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = context.getContentResolver().openOutputStream(uriSavedFile);
            try {                    //缓存数组
                byte[] a = new byte[1024];
                int len = -1;
                while ((len = in.read(a)) != -1) {
                    out.write(a);
                }
                //将缓存写入目标文件
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 29) {
            valuesFile.clear();
            valuesFile.put(MediaStore.Video.Media.IS_PENDING, 0);
            context.getContentResolver().update(uriSavedFile, valuesFile, null, null);
        } else {
            Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            final Uri localUri = Uri.fromFile(createdFile);
            localIntent.setData(localUri);
            context.sendBroadcast(localIntent);
        }
    }

}