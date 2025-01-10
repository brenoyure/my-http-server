package br.albatross.myhttpserver;

public interface MyServer {
    long connectionTimeout();
    boolean isRunning();
    void start();
    void stop();

}
