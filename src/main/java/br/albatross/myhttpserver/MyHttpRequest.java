package br.albatross.myhttpserver;

public class MyHttpRequest {

    private String method;
    private String uri;
    private String host;
    private String contentType;
    private int contentLength;
    private String body;
    private String httpVersion;

    public MyHttpRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public MyHttpRequest setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public MyHttpRequest setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }    

    public MyHttpRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public MyHttpRequest setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MyHttpRequest setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public MyHttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getUri() {
        return uri;
    }

    public String getHost() {
        return host;
    }

    public String getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getBody() {
        return body;
    }

}
