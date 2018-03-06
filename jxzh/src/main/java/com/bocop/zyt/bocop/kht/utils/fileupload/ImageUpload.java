package com.bocop.zyt.bocop.kht.utils.fileupload;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bocop.zyt.bocop.kht.bean.CameraBean;
import com.bocop.zyt.bocop.kht.config.LifeBaseConfig;
import com.bocop.zyt.bocop.kht.constants.LifeConstants;
import com.bocop.zyt.bocop.kht.utils.LifeUtil;
import com.bocop.zyt.jx.tools.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author xiaxinqing
 *         android:configChanges="keyboardHidden|orientation|screenSize"//
 */
public class ImageUpload {

    private static final String TAG = "ImageUpload: ";

    public ImageUpload() {

    }

    private String filePath;
    private CameraBean cameraBean;
    // private double qua;
    private long fileSize;
    private int parsew;
    private int parseh;
    private int compressHeight;
    private int compressWidth;
    private int thumbHeight;
    private int thumbWidth;
    private Activity activity;
    private ImageUploadCallBack callBack;
    private String imaType;
    private String sourceType;
    private String source;
    private Uri fileUri;
    private String timeStamp;
    private static String imageFile = "";
    private static int imageFileCount = 0;

    public void start(Activity activity, String jsMsg, String saveFilePath, boolean isCompressed,
                      ImageUploadCallBack callBack) {

        if (activity == null || TextUtils.isEmpty(jsMsg) || TextUtils.isEmpty(saveFilePath) || callBack == null) {

            throw new NullPointerException("All the parameters cannot be empty");
        }
        if (!checkJson(jsMsg)) {
            callBack.errorMsg(ResultCode.OTHER_ERROR, "json error");
            return;
        }
        this.filePath = saveFilePath;

        try {
            cameraBean = JSON.parseObject(jsMsg, CameraBean.class);
        } catch (Exception e) {
            callBack.errorMsg(ResultCode.OTHER_ERROR, e.toString());
        }
        this.activity = activity;
        this.callBack = callBack;
        choosestartup(isCompressed);
    }

    public void stop(Context context, int requestCode, int resultCode, Intent data, boolean isCompressed) {
        if (resultCode == Activity.RESULT_OK) {
            String picturePath = LifeUtil.getCallBackFilePath(context, requestCode, resultCode, data, fileUri);

            if (!TextUtils.isEmpty(picturePath) && requestCode == LifeConstants.REQUEST_CAMERA || requestCode == LifeConstants.REQUEST_PHOTO
                    | requestCode == LifeBaseConfig.CONTENT_REQUEST_CODE) {

                switch (requestCode) {
                    case LifeConstants.REQUEST_CAMERA:
                        source = "c";
                        compressPhoto(picturePath, isCompressed);
                        break;

                    case LifeConstants.REQUEST_PHOTO:
                        source = "p";
                        if (!TextUtils.isEmpty(picturePath)) {
                            compressPhoto(picturePath, isCompressed);
                        }
                        break;
                    case LifeBaseConfig.CONTENT_REQUEST_CODE:
                        source = "p";
                        if (!TextUtils.isEmpty(picturePath)) {
                            compressPhoto(picturePath, isCompressed);
                        }
                        break;
                }
            }
        }
    }


    public CameraBean getCameraBean() {
        return cameraBean;
    }

    private void compressPhoto(String picturePath, boolean isCompressed) {
        new compressPicture().execute(picturePath);
    }

    private void choosestartup(boolean isCompressed) {
        if (cameraBean == null) {
            callBack.errorMsg(ResultCode.OTHER_ERROR, "json format error");
            return;
        }
        if (!TextUtils.isEmpty(cameraBean.getSourceType())) {
            sourceType = cameraBean.getSourceType();
            switch (sourceType) {
                case "p":
                    if (checkData(isCompressed)) {
                        startPicture();
                    }
                    break;
                case "c":
                    if (checkData(isCompressed)) {
                        startCamera();
                    }
                    break;
                default:
                    callBack.errorMsg(ResultCode.IMAGE_SOURCE_TYPE_ERROR, "Source Type Error");
                    break;
            }
        } else {
            if (checkData(isCompressed)) {
                getChooseImgMethod();
            }
        }
    }

    private void startPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, LifeConstants.REQUEST_PHOTO);
    }

    public void startCamera() {
        String sdState = Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = Uri.fromFile(getOutputMediaFile(1));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            activity.startActivityForResult(intent, LifeConstants.REQUEST_CAMERA);
        } else {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "内存卡异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }


        try {
            activity.startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的TIFF文件"),
                    LifeBaseConfig.CONTENT_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "未找到相关应用市场", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }

    private void getChooseImgMethod() {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String[] chooserString = {"相机",
                        "从相册中新增照片"};
                showSelectDialog(activity, "", chooserString, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                startCamera();
                                break;
                            case 1:
                                startPicture();
                                break;
                            case 2:
                                showFileChooser();
                                break;
                        }
                    }
                });
            }
        });

    }

    private AlertDialog showSelectDialog(Context context, String title, String[] items,
                                         OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.show();
    }

    private boolean checkData(boolean isCompressed) {
        imaType = cameraBean.getImageFormat();
        if (!TextUtils.isEmpty(imaType)) {
            switch (imaType.toLowerCase()) {
                case "jpg":
                    break;
                case "png":
                    break;
                case "default":
                    break;
                default:
                    callBack.errorMsg(ResultCode.IMAGE_FORMAT_ERROR, "Image Format Error");
                    return false;
            }
        }

        compressHeight = Integer.parseInt(cameraBean.getCompressHeight());
        compressWidth = Integer.parseInt(cameraBean.getCompressWidth());
        thumbHeight = Integer.parseInt(cameraBean.getThumbnailHeight());
        thumbWidth = Integer.parseInt(cameraBean.getThumbnailWidth());
        String imageFile = cameraBean.getImageFile();
        String size = cameraBean.getFileSize();
        try {
            if (TextUtils.isEmpty(size)) {
                fileSize = 6 * 1024 * 1024L;
            } else {
                fileSize = (long) (Double.parseDouble(size) * 1024 * 1024);
                if (fileSize <= 0L) {
                    callBack.errorMsg(ResultCode.OTHER_ERROR, "Image Size Error");
                    return false;
                }
            }
            if (!TextUtils.isEmpty(imageFile)) {
                String regular = "[a-zA-Z_0-9]+";
                Pattern p = Pattern.compile(regular);
                Matcher m = p.matcher(imageFile);
                if (!m.matches()) {
                    callBack.errorMsg(ResultCode.IMAGE_NAME_ERROR, "Image Name Error");
                    return false;
                }
            }

            if (compressWidth != 0 && compressHeight != 0) {
                if (compressWidth > 1920) {
                    callBack.errorMsg(ResultCode.IMAGE_WIDTH_ERROR, "Image Width Error");
                    return false;
                }
                if (compressHeight > 1440) {
                    callBack.errorMsg(ResultCode.IMAGE_HEIGHT_ERROR, "Image Height Error");
                    return false;
                }
                if (compressWidth < 0) {
                    callBack.errorMsg(ResultCode.IMAGE_WIDTH_ERROR, "Image Width Error");
                    return false;
                }
                if (compressHeight < 0) {
                    callBack.errorMsg(ResultCode.IMAGE_HEIGHT_ERROR, "Image Height Error");
                    return false;
                }
            } else if (thumbWidth != 0 && thumbHeight != 0) {
                if (thumbWidth > 1920) {
                    callBack.errorMsg(ResultCode.IMAGE_WIDTH_ERROR, "Image Width Error");
                    return false;
                }
                if (thumbHeight > 1440) {
                    callBack.errorMsg(ResultCode.IMAGE_HEIGHT_ERROR, "Image Height Error");
                    return false;
                }
                if (thumbWidth < 0) {
                    callBack.errorMsg(ResultCode.IMAGE_WIDTH_ERROR, "Image Width Error");
                    return false;
                }
                if (thumbHeight < 0) {
                    callBack.errorMsg(ResultCode.IMAGE_HEIGHT_ERROR, "Image Height Error");
                    return false;
                }
            } else {
                parsew = 0;
                parseh = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.errorMsg(ResultCode.OTHER_ERROR, "Unknown Error");
            return false;

        }
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    private File getOutputMediaFile(int type) {
        // "/A_Test_Camera/"
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filePath);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    private boolean checkJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    String fileName = "";

    class compressPicture extends AsyncTask<String, Void, File> {

        @Override
        protected File doInBackground(String... params) {
            callBack.onStart(cameraBean);
            File file = new File(params[0]);
            timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            if (imageFile.equals(timeStamp)) {
                imageFileCount++;
            } else {
                imageFileCount = 0;
            }
            timeStamp = timeStamp + (String.valueOf(imageFileCount).length() > 2 ? imageFileCount : ("0" + imageFileCount));
            imageFile = timeStamp;
            String imageFormat = "";
            if (!(file.getName().toLowerCase().endsWith(".gif") || file.getName().toLowerCase().endsWith(".tif")
                    || file.getName().toLowerCase().endsWith(".tiff"))) {
                imageFormat = cameraBean.getImageFormat();
            }
            if (!TextUtils.isEmpty(imageFile) && !"default".equalsIgnoreCase(imageFormat) && !TextUtils.isEmpty(imageFormat)) {
                fileName = imageFile + "." + imageFormat;

            } else if (TextUtils.isEmpty(imageFile) && ("default".equalsIgnoreCase(imageFormat) || TextUtils.isEmpty(imageFormat))) {
                fileName = file.getName();
                int lastIndexOf = fileName.lastIndexOf(".");
                imaType = fileName.substring(lastIndexOf + 1, fileName.length());
            } else if (TextUtils.isEmpty(imageFile) && !"default".equalsIgnoreCase(imageFormat) && !TextUtils.isEmpty(imageFormat)) {
                fileName = file.getName();
                int lastIndexOf = fileName.lastIndexOf(".");
                String name = fileName.substring(0, lastIndexOf + 1);
                fileName = name + imageFormat;
            } else if (!TextUtils.isEmpty(imageFile) && ("default".equalsIgnoreCase(imageFormat) || TextUtils.isEmpty(imageFormat))) {
                fileName = file.getName();
                int lastIndexOf = fileName.lastIndexOf(".");
                String substring = fileName.substring(lastIndexOf, fileName.length());
                fileName = imageFile + substring;
            }
            try {

                final Object result = ImgUtils.compressImage(file, fileName, compressHeight, compressWidth, fileSize, imaType,
                        source);

                if (result instanceof String) {
                    final String resultStr = (String) result;
                    if (ResultCode.IMAGE_OUT_OF_MEMORY.equals(resultStr)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.errorMsg(ResultCode.IMAGE_TOO_LARGE, "The image is too large");
                            }
                        });
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.errorMsg(ResultCode.IMAGE_FORMAT_ERROR, resultStr);
                            }
                        });
                    }
                    return null;
                } else if (result instanceof File) {
                    if (((File) result).length() > fileSize) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.errorMsg(ResultCode.IMAGE_TOO_LARGE, "Can not compress to the given size");
                            }
                        });
                        return null;
                    }
                    return (File) result;
                }
            } catch (Exception e) {
                LogUtils.d(TAG + e.getMessage());
                e.printStackTrace();
            }
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    callBack.errorMsg(ResultCode.IMAGE_FORMAT_ERROR, "Image compress error");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(File result) {
            if (result != null) {
                LogUtils.d(TAG + "FilePath -> " + result.getAbsolutePath());
                try {
                    byte[] bytes = getBytesFromFile(result);
                    float a=(float) (bytes.length/(1024.0*1024.0));
                    callBack.onFinish(result, fileName, String.format("%.2f", a), cameraBean.getArgs());
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.errorMsg(ResultCode.OTHER_ERROR, "Compress Error");
                }
            }
        }

    }

    public interface ImageUploadCallBack {
        void onStart(CameraBean bean);

        void onFinish(File file, String fileName, String imgSize, String extraInfo);// 调用stop 方法时才会触发该方法

        void errorMsg(String state, String errorMsg);

    }

    /**
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] getBytesFromFile(File file) throws Exception {
        if (file != null && !(file.length() > Integer.MAX_VALUE)) {
            try {
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                int offest = 0;
                int numRead = 0;
                while (offest < bytes.length && (numRead = is.read(bytes, offest, bytes.length - offest)) >= 0) {
                    offest += numRead;
                }
                is.close();
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new Exception("File is null or file is to large");
    }
}
