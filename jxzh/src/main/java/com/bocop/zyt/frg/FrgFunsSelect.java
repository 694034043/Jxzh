//
//  FrgFunsSelect
//
//  Created by Administrator on 2017-09-15 11:28:24
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.frg;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bocop.zyt.F;
import com.bocop.zyt.R;
import com.bocop.zyt.ada.AdaFuns;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.fmodule.utils.IHttpClient;
import com.bocop.zyt.view.GalleryView;
import com.gjbank.proto.ApisFactory;
import com.item.proto.MModuleIndex;
import com.item.proto.MTopModule;
import com.item.proto.MTopModuleList;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.config.BaseConfig;
import com.mdx.framework.server.api.Son;
import com.mdx.framework.utility.Helper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FrgFunsSelect extends BaseFrg {

    public TextView tv_enter;
    public GalleryView galleryView;
    private MTopModule module;
    private String mp3File;
    private String MP3Dir = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/zhongyht";

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_funs_select);
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        tv_enter = (TextView) findViewById(R.id.tv_enter);
        galleryView = (GalleryView) findViewById(R.id.galleryView);


    }

    public void loaddata() {

        mp3File = MP3Dir + "/yct.mp3";
        File d = new File(MP3Dir);
        if (!d.exists()) {
            d.mkdirs();
        }
        if (TextUtils.isEmpty(F.choosecode)) {
            ApisFactory.getApiMTopModuleList().load(getContext(), FrgFunsSelect.this, "TopModuleList", "", F.code);
        } else {
            ApisFactory.getApiMTopModuleList().load(getContext(), FrgFunsSelect.this, "TopModuleList", F.choosecode, "");
        }


    }

    public void TopModuleList(MTopModuleList data, Son s) {
        if (data != null && s.getError() == 0) {
            if (data.list.size() > 1) {
                F.setChooseCode("all");
            } else {
                F.setChooseCode(data.list.get(0).id);
            }

            galleryView.setAdapter(new AdaFuns(getContext(), data.list));
            galleryView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    module = (MTopModule) galleryView.getAdapter().getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            galleryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    module = (MTopModule) galleryView.getAdapter().getItem(position);
                    if (module.code.equals("yct")) {
                        ApisFactory.getApiMModuleIndex().load(getContext(), FrgFunsSelect.this, "ModuleIndex", module.id);
                    } else {
                        Helper.startActivity(getContext(), FrgYhtHome.class, NoTitleAct.class, "data", module);
                    }
                }

            });
            tv_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (module.code.equals("yct")) {
                        ApisFactory.getApiMModuleIndex().load(getContext(), FrgFunsSelect.this, "ModuleIndex", module.id);
                    } else {
                        Helper.startActivity(getContext(), FrgYhtHome.class, NoTitleAct.class, "data", module);
                    }

                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void ModuleIndex(MModuleIndex data, Son s) {
        if (data != null && s.getError() == 0) {
            if (!TextUtils.isEmpty(data.music)) {
                downloadMp3(BaseConfig.getUri() + "/bank/download.do?id=" + data.music, mp3File, new IHttpClient.Callback() {

                    @Override
                    public void suc(String ret) {

                    }

                    @Override
                    public void fail(String ret) {
                    }
                });
                Helper.startActivity(getContext(), FrgYhtHome.class, NoTitleAct.class, "data", module);

            }
        }

    }

    private void downloadMp3(String url, final String file1, final IHttpClient.Callback callback) {

        final String mp3File = file1 + "temp";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @SuppressWarnings("resource")
            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(mp3File);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
//						int progress = (int) (sum * 1.0f / total * 100);
//						Log.d("h_bl", "progress=" + progress);

                    }
                    fos.flush();
                    ILOG.log_4_7("h_bl" + "文件下载成功");
                    file.renameTo(new File(file1));
                    callback.suc(mp3File);
                } catch (Exception e) {
                    ILOG.log_4_7("h_bl" + "文件下载失败" + e.toString());
                    callback.fail(e.toString());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                Log.d("h_bl", "onFailure");
                callback.fail("onFailure");
            }

        });

    }
}