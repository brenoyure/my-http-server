package br.albatross.myhttpserver;

import java.util.regex.Pattern;

public class MyHttpRequestHostSetter implements MyHttpRequestSetter {

    private static final String HOST_REGEX = "(?:Host\\:\\s)(?<Host>.+)";
    private static final Pattern pattern = Pattern.compile(HOST_REGEX);
    private static final String HOST_CAPTURE_GROUP = "Host";

    @Override
    public void setRequestField(String request, MyHttpRequest myHttpRequest) {
        myHttpRequest.setHost(pattern.matcher(request).group(HOST_CAPTURE_GROUP));
    }

}
