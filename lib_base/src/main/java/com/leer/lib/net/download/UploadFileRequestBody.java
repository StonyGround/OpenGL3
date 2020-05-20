package com.leer.lib.net.download;

/**
 * 扩展OkHttp的请求体，实现上传时的进度提示
 */

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


public class UploadFileRequestBody<T> extends RequestBody {


    private RequestBody mRequestBody;
    private FileUploadObserver<T> fileUploadObserver;
    private int partNum;

    public UploadFileRequestBody(File file, FileUploadObserver<T> fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        this.fileUploadObserver = fileUploadObserver;
    }

    public UploadFileRequestBody(byte[] content, int partNum,
                                 FileUploadObserver<T> fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"),
                content);
        this.fileUploadObserver = fileUploadObserver;
        this.partNum = partNum;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        //写入
        mRequestBody.writeTo(bufferedSink);
        //刷新
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (fileUploadObserver != null) {
                fileUploadObserver.onProgressChange(partNum, bytesWritten, contentLength());
            }

        }

    }
}
