package br.albatross.myhttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyServerRequestInputStreamtoStringConverter implements MyServerRequestConverter<InputStream, String> {

    public String convert(InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder sb = new StringBuilder();
            while (inputStream.available() > 0) {
                sb.append(br.readLine());
            }
            return sb.toString();
        } catch (IOException e) { throw new RuntimeException(e); }

    }

}
