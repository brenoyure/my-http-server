package br.albatross.myhttpserver.response;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MyHttpResponse {

    private String version;
    private int code;
    private String status;
    private String contentType;
    private String body;

    public MyHttpResponse() {
        this.version = "HTTP/1.1";
        this.code = 200;
        this.status = "OK";
        this.contentType = "text/plain";
    }

    public MyHttpResponse(String version, int code, String status, String contentType, String body) {
        this.version = version;
        this.code = code;
        this.status = status;
        this.contentType = (contentType == null || contentType.isBlank()) ? "text/plain" : contentType;
        this.body = body;
    }

    public MyHttpResponse setVersion(String version) {
        this.version = version;
        return this;
    }

    public MyHttpResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public MyHttpResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public MyHttpResponse setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MyHttpResponse setBody(String body) {
        this.body = body;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append(version.concat(" ")).append(code).append(" ".concat(status))
            .append(System.lineSeparator())
            .append("Date: ").append( OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME) )
            .append(System.lineSeparator())
            .append("Server: Albatross MyHttpServer 1.0")
            .append(System.lineSeparator())
            .append("X-Content-Type-Options: nosniff")
            .append(System.lineSeparator())
            .append("Content-Type: ").append(contentType)
            .append(System.lineSeparator())
            .append(System.lineSeparator());
        sb.append(body);

        return sb.toString();
    }

    public byte[] toClientResponse() {
        return this.toString().getBytes(StandardCharsets.UTF_8);
    }

}
