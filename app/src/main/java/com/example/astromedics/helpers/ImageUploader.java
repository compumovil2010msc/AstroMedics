package com.example.astromedics.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class ImageUploader {
    public static void uploadAndGetURL(ImageView imageView, URLListener urlListener) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,
                        100,
                        baos);
        byte[] imageInByte = baos.toByteArray();

        String requestId = MediaManager.get()
                                       .upload(imageInByte)
                                       .callback(new UploadCallback() {
                                           @Override
                                           public void onStart(String requestId) {

                                           }

                                           @Override
                                           public void onProgress(String requestId, long bytes, long totalBytes) {

                                           }

                                           @Override
                                           public void onSuccess(String requestId, Map resultData) {
                                               urlListener.onURLObtained(resultData.get("url")
                                                                                   .toString());
                                           }

                                           @Override
                                           public void onError(String requestId, ErrorInfo error) {
                                               urlListener.onError(error);
                                           }

                                           @Override
                                           public void onReschedule(String requestId, ErrorInfo error) {

                                           }
                                       })
                                       .dispatch();
    }

    public interface URLListener {
        void onURLObtained(String url);

        void onError(ErrorInfo error);
    }
}
