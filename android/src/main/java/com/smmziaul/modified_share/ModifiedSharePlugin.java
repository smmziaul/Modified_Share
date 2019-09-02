package com.smmziaul.modified_share;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.Manifest;
import android.content.Intent;

import android.net.Uri;
import android.util.Log;
import android.content.pm.PackageManager;

import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

/** ModifiedSharePlugin */
public class ModifiedSharePlugin implements MethodCallHandler {

  private static final String CHANNEL = "modified_share";
  private static final String TAG = "Modified_Share";

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
    channel.setMethodCallHandler(new ModifiedSharePlugin(registrar));
  }

  private final Registrar mRegistrar;

  private ModifiedSharePlugin(Registrar registrar) {
    this.mRegistrar = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("share")) {
      share(call, result);
    } else if (call.method.equals("showToast")) {
      showToast(call, result);
    } else if (call.method.equals("checkWriteStoragePermission")) {
      boolean isWriteStoragePermissionGranted = checkWriteStoragePermission(result);
      result.success(Boolean.toString(isWriteStoragePermissionGranted));
    } else {
      result.notImplemented();
    }
  }

  public void showToast(MethodCall call, Result result) {
    String msgToShow = call.argument("msgToShow");
    Toast toast = Toast.makeText(mRegistrar.context(), msgToShow, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
  }

  public boolean checkWriteStoragePermission(Result result) {
    if (Build.VERSION.SDK_INT >= 23) {
      if (ContextCompat.checkSelfPermission(mRegistrar.context(),
          android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        Log.v(TAG, "write storage permission is granted");
        return true;
      } else {
        Log.v(TAG, "write storage permission is revoked");
        androidx.core.app.ActivityCompat.requestPermissions(mRegistrar.activity(),
            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 2);
        return false;
      }
    } else { // permission is automatically granted on sdk<23 upon installation
      Log.v(TAG, "write storage permission is granted");
      return true;
    }
  }

  public void share(MethodCall call, final Result result) {
    final String imageURL = call.argument("imageURL"), content = call.argument("content"),
        onlyWhatsApp = call.argument("onlyWhatsApp"), subject = call.argument("subject");

    androidx.core.app.ActivityCompat.requestPermissions(mRegistrar.activity(),
        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 6);

    Target target = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Bitmap bmp = bitmap;
        String path = MediaStore.Images.Media.insertImage(mRegistrar.context().getContentResolver(), bmp, "SomeText",
            null);
        Log.v(TAG, "image path: " + path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        Uri screenshotUri = Uri.parse(path);
        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("image/*");
        if (onlyWhatsApp.equals("true")) {
          try {
            intent.setPackage("com.whatsapp");
            mRegistrar.context().startActivity(intent);
          } catch (android.content.ActivityNotFoundException exception) {
            Log.v(TAG, "WhatsApp is not installed");

          }
        } else {
          Intent chooserIntent = Intent.createChooser(intent, "Share using");
          chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          mRegistrar.context().startActivity(chooserIntent);
        }
        result.success("modified share success!");
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {
        result.success("modified share failed");
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.v(TAG, "preparing to load the remote image");
      }
    };
    Picasso.with(mRegistrar.context()).load(imageURL).into(target);
  }

}
