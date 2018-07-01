package com.swolf.librarybase.photo.popupWindow;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import com.swolf.librarybase.R;
import com.swolf.librarybase.photo.NYSavePhotoUtil;
import com.swolf.librarybase.dialogPopup.popupWindow.NYPopupWindowUtil;

import java.io.File;


/**
 * 相册拍照工具PopupWindow
 * Created by LiuYi-15973602714
 */
public class NYPickPhotoPopupWindow {

    /**
     *
     * 注册 存储照的容器
     *
     * name:属性值，固定写法
     * authorities:组件标识，按照江湖规矩,都以包名开头,避免和其它应用发生冲突。和FileProvider.getUriForFile()方法的第二个参数一致
     * exported:要求必须为false，为true则会报安全异常。
     * grantUriPermissions:true，表示授予 URI 临时访问权限。
     *
     * <provider
     * android:name="android.support.v4.content.FileProvider"
     * android:authorities="{项目包名}.fileprovider"
     * android:exported="false"
     * android:grantUriPermissions="true">
     *
     * <!--指定Uri的共享路径-->
     * <meta-data
     * android:name="android.support.FILE_PROVIDER_PATHS"
     * android:resource="@xml/file_paths" />
     * </provider>
     *
     * @param authority "{项目包名}.fileprovider";
     */

    /**
     * 相册
     */
    public static final int REQUEST_CODE_PICKPHOTOFROMGALLERY = 2000;
    /**
     * 拍照
     */
    public static final int REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO = 2001;
    /**
     * 裁剪
     */
    public static final int REQUEST_CODE_PHOTOZOOMCLIP = 2002;
    /**
     * 当前的文件
     */
    public static File currentFile = null;

    public static void newFileInfo(String ext) {
        File cameraDir = new File(Environment.getExternalStorageDirectory() + File.separator + "cameraDir" + File.separator);
        /** 使用照相机拍摄照片作为头像时会使用到这个路径 */
        if (!cameraDir.exists()) {
            cameraDir.mkdirs();
        }
        StringBuffer sb = new StringBuffer();
        if (ext != null && ext.length() > 0) {
            sb.append(ext);
            sb.append("_");
        }
        sb.append("IMG_");
        sb.append(System.currentTimeMillis());
        sb.append(".jpg");
        String fileName = sb.toString();
        currentFile = new File(cameraDir, fileName);
    }

    /**
     * 显示拍照popupWindow
     */
    public static void showTakephotoPopupWindow(final Activity activity, View clickParentView) {
        final NYPopupWindowUtil pop = new NYPopupWindowUtil();
        View view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto, null);
        TextView textView2 = (TextView) view.findViewById(R.id.textView_photo_2);
        textView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                newFileInfo("");
                pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO);
            }
        });
        pop.showScreenBottom(clickParentView, view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0, null);
    }

    /**
     * 显示相册与拍照Cancel popupWindow
     */
    public static void showTakephotoGalleryPhotoPopupWindow(final Activity activity, View clickParentView) {
        final NYPopupWindowUtil pop = new NYPopupWindowUtil();
        View view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto_gallery, null);
        TextView textView1 = (TextView) view.findViewById(R.id.textView_photo_1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView_photo_2);
        textView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                pickPhotoFromGallery(activity, REQUEST_CODE_PICKPHOTOFROMGALLERY);
            }
        });
        textView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                newFileInfo("");
                pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO);
            }
        });
        pop.showScreenBottom(clickParentView, view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0, null);
    }

    /**
     * 显示相册与拍照popupWindow
     */
    public static void showTakephotoGalleryCancelPopupWindow(final Activity activity, View clickParentView) {
        final NYPopupWindowUtil pop = new NYPopupWindowUtil();
        View view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto_gallery_cancel, null);
        TextView textView3 = (TextView) view.findViewById(R.id.textView_photo_3);
        TextView textView2 = (TextView) view.findViewById(R.id.textView_photo_2);
        TextView textView1 = (TextView) view.findViewById(R.id.textView_photo_1);
        textView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                pickPhotoFromGallery(activity, REQUEST_CODE_PICKPHOTOFROMGALLERY);
            }
        });
        textView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                newFileInfo("");
                pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO);
            }
        });
        textView3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
            }
        });
        pop.showScreenBottom(clickParentView, view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0, null);
    }

    /**
     * outputX * outputY <= 129900
     */
    public static void activityResult(Activity activity, int requestCode, int resultCode, Intent data,
                                      ImageView imageView, int outputX, int outputY, NYIActivityResultHandler handler) {
        if (resultCode == 0) {
            if (handler != null) {
                handler.cancel();
            }
            return;
        }
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(activity,"操作失败",Toast.LENGTH_LONG).show();

            if (handler != null) {
                handler.fail();
            }
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PICKPHOTOFROMGALLERY:// 相册..结果
                result_pickPhotoFromGallery(activity, resultCode, data, imageView, outputX, outputY, handler);
                break;
            case REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO:// 拍照..结果
                result_pickPhotoFromTakePhoto(activity, resultCode, imageView, outputX, outputY, handler);
                break;
            case REQUEST_CODE_PHOTOZOOMCLIP:// 裁剪图片
                result_photoZoomClip(activity, resultCode, data, imageView, handler);
                break;
            default:
                break;
        }
    }


    //////////

    /**
     * Gallery 相册
     */
    public static void pickPhotoFromGallery(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!NYPickPhotoUtil.checkSelfPermissionGallery6(activity)) {
                return;
            }
            //打开相册
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activity.startActivityForResult(intent, requestCode);

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Take Photo 照相
     */
    public static void pickPhotoFromTakePhoto(Activity activity, File imageFile, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!NYPickPhotoUtil.checkSelfPermissionTakePhoto6(activity)) {
                return;
            }
            Uri imageUri = Uri.fromFile(imageFile);
            //判断Android版本是否是Android7.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authority = activity.getPackageName() + ".generic.file.provider";
                imageUri = FileProvider.getUriForFile(activity, authority, imageFile);
            }
            //启动相机程序
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, requestCode);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Clip Zoom Photo 相册 裁剪图片 outputX * outputY <= 129900
     *
     * @param activity
     * @param file
     * @param outputX
     * @param outputY
     * @param saveFile    6.0及以上系统使用
     * @param requestCode
     */
    public static void photoZoomClip(Activity activity, File file, int outputX, int outputY, File saveFile, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(NYPickPhotoUtil.getImageContentUri(activity, file), "image/*");//自己使用Content Uri替换File Uri
                intent.putExtra("crop", "true");
                //aspectX==aspectY?裁剪框可能为圆形:为矩形
                intent.putExtra("aspectX", outputX);
                intent.putExtra("aspectY", outputY);
                intent.putExtra("outputX", outputX);
                intent.putExtra("outputY", outputY);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));//定义输出的File Uri
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true);
                activity.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                String errorMessage = "Your device doesn't support the crop action!";
                Toast.makeText(activity,errorMessage,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            intent.putExtra("crop", "true");
            //aspectX==aspectY?裁剪框可能为圆形:为矩形
            intent.putExtra("aspectX", outputX);
            intent.putExtra("aspectY", outputY);
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, requestCode);
        }

    }

////////////////////////////



    ////////////////////////////


    public static void result_pickPhotoFromGallery(Activity activity, int resultCode, Intent data,
                                                   ImageView imageView, int outputX, int outputY, NYIActivityResultHandler handler) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String imagePath = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //Android4.4及以上版本
                imagePath = NYPickPhotoUtil.uriToPath6(activity, data.getData());
            } else {
                //Android4.4以下版本
                imagePath = NYPickPhotoUtil.getImagePath6(activity, data.getData(), null);
            }
            currentFile = new File(imagePath);
//            NYPhotoUtil.rotatePicture(currentFile.getAbsolutePath());
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bm = BitmapFactory.decodeFile(currentFile.getAbsolutePath(), options);
                    imageView.setImageBitmap(bm);
                }
                if (handler != null) {
                    handler.pickPhotoFromGallerySuccess();
                }
            } else {
                newFileInfo("C");
                photoZoomClip(activity, new File(imagePath), outputX, outputY, currentFile, REQUEST_CODE_PHOTOZOOMCLIP);
            }
        } else {
            Uri uri = data.getData();
            String path = uri.getPath();
            String imagePath = "";
            if (path != null && path.lastIndexOf(".jpg") == path.length() - 4) {
                imagePath = path;
            } else {
                imagePath = NYPickPhotoUtil.getFilePathFromUri(activity, data.getData());
            }
            currentFile = new File(imagePath);
//            NYPhotoUtil.rotatePicture(currentFile.getAbsolutePath());
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bm = BitmapFactory.decodeFile(currentFile.getAbsolutePath(), options);
                    imageView.setImageBitmap(bm);
                }
                if (handler != null) {
                    handler.pickPhotoFromGallerySuccess();
                }
            } else {
                photoZoomClip(activity, currentFile, outputX, outputY, null, REQUEST_CODE_PHOTOZOOMCLIP);
            }
        }
    }

    public static void result_pickPhotoFromTakePhoto(Activity activity, int resultCode,
                                                     ImageView imageView, int outputX, int outputY, NYIActivityResultHandler handler) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            NYPhotoUtil.rotatePicture(currentFile.getAbsolutePath());
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bm = BitmapFactory.decodeFile(currentFile.getAbsolutePath(), options);
                    imageView.setImageBitmap(bm);
                }
                if (handler != null) {
                    handler.pickPhotoFromTakePhotoSuccess();
                }
            } else {
                File file = new File(currentFile.getAbsolutePath());
                newFileInfo("C");
                photoZoomClip(activity, file, outputX, outputY, currentFile, REQUEST_CODE_PHOTOZOOMCLIP);
            }
        } else {
//            NYPhotoUtil.rotatePicture(currentFile.getAbsolutePath());
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bm = BitmapFactory.decodeFile(currentFile.getAbsolutePath(), options);
                    imageView.setImageBitmap(bm);
                }
                if (handler != null) {
                    handler.pickPhotoFromTakePhotoSuccess();
                }
            } else {
                photoZoomClip(activity, currentFile, outputX, outputY, null, REQUEST_CODE_PHOTOZOOMCLIP);
            }
        }
    }

    public static void result_photoZoomClip(Activity activity, int resultCode, Intent data,
                                            ImageView imageView, NYIActivityResultHandler handler) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String imagePath = currentFile.getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            if (handler != null) {
                handler.photoZoomClipSuccess();
            }
        } else {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bitmap = extras.getParcelable("data");
                newFileInfo("C");
                NYSavePhotoUtil.saveImage2File(bitmap, Bitmap.CompressFormat.JPEG, currentFile);
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
                if (handler != null) {
                    handler.photoZoomClipSuccess();
                }
            } else {
                Toast.makeText(activity,"裁剪图片失败,没有获取到裁剪的图片",Toast.LENGTH_LONG).show();
            }
        }
    }







}
