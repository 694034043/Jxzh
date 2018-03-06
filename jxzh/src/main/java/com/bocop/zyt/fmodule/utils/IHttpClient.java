package com.bocop.zyt.fmodule.utils;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by lt on 2016/12/21.
 */
public class IHttpClient {

	private static Gson gson = new Gson();
	private static OkHttpClient defaultHttpClient;
	public static final MediaType MEDIATYPEXML
			= MediaType.parse("text/xml; charset=utf-8");

	public static OkHttpClient getDefaultHttpClient() {

		if (defaultHttpClient == null) {
			defaultHttpClient = new OkHttpClient();
		}

		return defaultHttpClient;

	}

	/**
	 * 只支持json接口的post,同步方法，不可取消
	 *
	 * @param url
	 * 			@param req @param resp @param <T> @return @throws
	 */
	public static <T> T postAysn(String url, Object req, Class<T> resp) throws IOException {

		return postAysn(null, url, req, resp);
	}

	/**
	 * 重载方法，可自定义httpclient
	 *
	 * @param okHttpClient
	 * @param url
	 * @param req
	 * @param resp
	 * @param <T>
	 * @return
	 * @throws IOException
	 */

	public static <T> T postAysn(OkHttpClient okHttpClient, String url, Object req, Class<T> resp) throws IOException {

		Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
		}.getType());

		StringBuilder params = new StringBuilder();
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {

			client = getDefaultHttpClient();
		}

		FormEncodingBuilder builder = new FormEncodingBuilder();
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			params.append(key).append("=").append(map.get(key)).append("&");
			builder.add(key, map.get(key));
			sb.append(key).append("=").append(map.get(key)).append("&");
		}

		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();

		Response ret = client.newCall(request).execute();
		String strRet = ret.body().string();
		// System.out.println(sb.toString()+" ### "+strRet);
		return gson.fromJson(strRet, resp);
	}

	public interface Callback {

		public void suc(String ret);

		public void fail(String ret);
	}

	public static void postAysn(OkHttpClient okHttpClient, String url, Object req, final Callback callback) {
		Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
		}.getType());

		StringBuilder params = new StringBuilder();
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {

			client = getDefaultHttpClient();
		}

		FormEncodingBuilder builder = new FormEncodingBuilder();
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			params.append(key).append("=").append(map.get(key)).append("&");
			builder.add(key, map.get(key));
			sb.append(key).append("=").append(map.get(key)).append("&");
		}

		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();

		client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				callback.fail("网络异常");
			}

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				callback.suc(response.body().string());
			}
		});
	}

	public static void postAysnMainThread(OkHttpClient okHttpClient, String url, Object req, final Callback callback) {
		Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
		}.getType());

		StringBuilder params = new StringBuilder();
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {

			client = getDefaultHttpClient();
		}

		FormEncodingBuilder builder = new FormEncodingBuilder();
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			params.append(key).append("=").append(map.get(key)).append("&");
			builder.add(key, map.get(key));
			sb.append(key).append("=").append(map.get(key)).append("&");
		}

		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();

		client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.fail("网络异常");
					}
				});
			}

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				final String ret = response.body().string();
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.suc(ret);
					}
				});
			}
		});
	}

	public static void callPostMethod(final String url, String params,MediaType mediaType, final Callback callback){
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.create(mediaType, params))
				.build();
		getDefaultHttpClient().newCall(request).enqueue(new com.squareup.okhttp.Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				callback.suc(arg0.body().toString());
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				callback.fail(arg0.body().toString()+"\n"+arg1.getMessage());
			}
		});
	}

	public static void aysnMainThread(OkHttpClient okHttpClient, String url, String postBody, final Callback callback) {
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {
			client = getDefaultHttpClient();
		}

		Request request = new Request.Builder().
				url(url)
				.post(RequestBody.create(
						MediaType.parse("text/plain"),
						postBody))
				.build();

		client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.fail("网络异常");
					}
				});
			}

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				final String ret = response.body().string();
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.suc(ret);
					}
				});
			}
		});
	}

	public static void getAysn(OkHttpClient okHttpClient, String url, Object req, final Callback callback) {
		Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
		}.getType());

		StringBuilder params = new StringBuilder();
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {

			client = getDefaultHttpClient();
		}

		FormEncodingBuilder builder = new FormEncodingBuilder();
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			params.append(key).append("=").append(map.get(key)).append("&");
			builder.add(key, map.get(key));
			sb.append(key).append("=").append(map.get(key)).append("&");
		}

		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url + "?" + sb.toString()).get().build();

		client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				callback.fail("网络异常");
			}

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				callback.suc(response.body().string());
			}
		});
	}

	public static Handler handler = new Handler(Looper.getMainLooper());

	public static void getAysnMainThread(OkHttpClient okHttpClient, String url, Object req, final Callback callback) {
		if (req == null) {

			OkHttpClient client = null;
			if (okHttpClient != null) {
				client = okHttpClient;
			} else {

				client = getDefaultHttpClient();
			}

			Request request = new Request.Builder().url(url).get().build();

			client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
				@Override
				public void onFailure(Request arg0, IOException arg1) {
					// TODO Auto-generated method stub
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.fail("网络异常");
						}
					});
				}

				@Override
				public void onResponse(Response response) throws IOException {
					// TODO Auto-generated method stub
					final String ret = response.body().string();
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.suc(ret);
						}
					});
				}
			});

		} else {
			Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
			}.getType());

			StringBuilder params = new StringBuilder();
			OkHttpClient client = null;
			if (okHttpClient != null) {
				client = okHttpClient;
			} else {

				client = getDefaultHttpClient();
			}

			FormEncodingBuilder builder = new FormEncodingBuilder();
			StringBuilder sb = new StringBuilder();
			for (String key : map.keySet()) {
				params.append(key).append("=").append(map.get(key)).append("&");
				builder.add(key, map.get(key));
				sb.append(key).append("=").append(map.get(key)).append("&");
			}

			RequestBody body = builder.build();
			Request request = new Request.Builder().url(url + "?" + sb.toString()).get().build();
			client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

				@Override
				public void onResponse(Response response) throws IOException {
					// TODO Auto-generated method stub
					final String ret = response.body().string();
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.suc(ret);
						}
					});
				}

				@Override
				public void onFailure(Request arg0, IOException arg1) {
					// TODO Auto-generated method stub
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.fail("网络异常");
						}
					});
				}
			});

		}
	}

	/**
	 * 只获取json 以字符串的方式返回
	 *
	 * @param url
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static String postRetString(String url, Object req) throws IOException {
		return postRetString(null, url, req);

	}

	/**
	 * @param okHttpClient
	 * @param url
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static String postRetString(OkHttpClient okHttpClient, String url, Object req) throws IOException {

		Map<String, String> map = gson.fromJson(gson.toJson(req), new TypeToken<Map<String, String>>() {
		}.getType());

		StringBuilder params = new StringBuilder();
		OkHttpClient client = null;
		if (okHttpClient != null) {
			client = okHttpClient;
		} else {

			client = getDefaultHttpClient();
		}

		FormEncodingBuilder builder = new FormEncodingBuilder();
		StringBuilder sb = new StringBuilder();
		if(map!=null){
			for (String key : map.keySet()) {
				params.append(key).append("=").append(map.get(key)).append("&");
				builder.add(key, map.get(key));
				sb.append(key).append("=").append(map.get(key)).append("&");
			}
		}
		System.out.println(sb.toString());
		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();

		Response ret = client.newCall(request).execute();
		return ret.body().string();

	}

}
