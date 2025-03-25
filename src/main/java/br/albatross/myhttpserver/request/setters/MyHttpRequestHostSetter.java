package br.albatross.myhttpserver.request.setters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.albatross.myhttpserver.request.MyHttpRequest;

public class MyHttpRequestHostSetter implements MyHttpRequestSetter {

    private static final String HOST_REGEX = "(?:Host\\:\\s)(?<Host>.+)";
    private static final Pattern pattern = Pattern.compile(HOST_REGEX);
    private static final String HOST_CAPTURE_GROUP = "Host";

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        Matcher requestMatcher = pattern.matcher(request);
        requestMatcher.find();
        myHttpRequest.setHost(requestMatcher.group(HOST_CAPTURE_GROUP));
    }

}
