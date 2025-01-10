package br.albatross.myhttpserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHttpRequestMethodUriAndHttpVersionSetter implements MyHttpRequestSetter {

    private static final String METHOD_CAPTURE_GROUP = "Method";
    private static final String URI_CAPTURE_GROUP = "Uri";
    private static final String VERSION_CAPTURE_GROUP = "Version";

    private static final String METHOD_URI_VERSION_REQ_HEADER_REGEX = 
            "(?<Method>GET|POST|PUT|UPDATE|DELETE|OPTIONS|HEAD)\\s(?<Uri>.+)\\s(?<Version>HTTP[0-9./]+)";
    private static final Pattern pattern = 
            Pattern.compile(METHOD_URI_VERSION_REQ_HEADER_REGEX);

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        Matcher requestMatcher = pattern.matcher(request);
        myHttpRequest
            .setMethod(requestMatcher.group(METHOD_CAPTURE_GROUP))
            .setUri(requestMatcher.group(URI_CAPTURE_GROUP))
            .setHttpVersion(requestMatcher.group(VERSION_CAPTURE_GROUP));
    }

}
