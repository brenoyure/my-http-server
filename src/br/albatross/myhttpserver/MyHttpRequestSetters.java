package br.albatross.myhttpserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class MyHttpRequestSetters {

    private static final List<MyHttpRequestSetter> setters = new LinkedList<>();

    public static Iterable<MyHttpRequestSetter> getSetters() {
        if (setters.isEmpty()) {

            add(new MyHttpRequestMethodUriAndHttpVersionSetter());
            add(new MyHttpRequestHostSetter());
            add(new MyHttpRequestContentLengthSetter());
            add(new MyHttpRequestContentTypeSetter());
            add(new MyHttpRequestBodySetter());

        }
        return Collections.unmodifiableList(setters);
    }

    private static void add(MyHttpRequestSetter myHttpRequestSetter) {
        setters.add(myHttpRequestSetter);
    }

}
