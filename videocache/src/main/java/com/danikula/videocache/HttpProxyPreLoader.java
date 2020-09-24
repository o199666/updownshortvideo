package com.danikula.videocache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Request;
import okhttp3.Response;

public class HttpProxyPreLoader {

    private static String TAG = "HttpProxyPreLoader";
    protected ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    static final String preUrlPx = "_HttpProxyPre";
    private Map<String, PreRunnable> runnableMap = new HashMap<>();

    public boolean isLoading(String url) {
        return runnableMap.get(url) != null;
    }

    public void startLoad(String url, int percentsPreLoad, long totalLen) {
        LogU.d("开始预下载　total " + url);
        PreRunnable runnable = new PreRunnable(url, percentsPreLoad,totalLen);
        runnableMap.put(url, runnable);
        cachedThreadPool.execute(runnable);
    }

    public int getPercentsPreLoad(String url) {
        PreRunnable runnable = runnableMap.get(url);
        if (runnable == null) {
            return 0;
        } else {
            return runnable.percentsPreLoad;
        }
    }

    public void stopLoad(String url) {
        PreRunnable runnable = runnableMap.get(url);
        if (runnable != null) {
            runnable.stop = true;
            LogU.d("停止预下载　total " + url);
        }
    }


    class PreRunnable implements Runnable {

        private String url;
        private int percentsPreLoad;

        protected boolean stop = false;
        private long len;

        public PreRunnable(String url, int percentsPreLoad, Long total) {
            this.url = url;
            this.len = total;
            this.percentsPreLoad = percentsPreLoad;
        }

        @Override
        public void run() {
            final Request request = new Request.Builder()
                    .url(url)
                    .head()
                    .build();

            try {
//                Response response = OkManager.getInstance().client.newCall(request).execute();
//                long length = Long.parseLong(response.header("content-length"));//获取文件长度
//                long targetLen = length * (percentsPreLoad / 100L);
                int targetLen = (int) (len * (percentsPreLoad / 100.0));
                ;//length * (percentsPreLoad / 100L);
                Request requestLoad = new Request.Builder()
                        .url(url)
                        .addHeader("Range", String.format("bytes=%d-%d", 0, targetLen))
                        .build();

                Response responseLoad = OkManager.getInstance().client.newCall(requestLoad).execute();
                InputStream inputStream = responseLoad.body().byteStream();//获取流


                final long M = 1024;
                byte[] bytes = new byte[(int) M];
                long seek = 0;
                long total = 0;
                for (; ; ) {
                    int readCount = inputStream.read(bytes);
                    total += readCount;
                    seek += readCount;
                    LogU.d("预下载客户端　total " + total+"        应该下载　"+targetLen +"   readCount " +readCount);
                    if (readCount == 0) {
                        continue;
                    }
                    if (total > targetLen) {
                        break;
                    }
                    if (readCount == -1) {
                        inputStream.close();
                        break;
                    }
                    if (stop) {
                        inputStream.close();
                        break;
                    }



                }

                responseLoad.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                runnableMap.remove(url);
            }
        }
    }

}
