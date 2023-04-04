package com.gitHubClient.JsonEncoder;

import java.io.OutputStream;

public interface JsonWriter<T> {
    public String toJson(T obj);

    public void streamWrite(OutputStream os, T obj) ;
}
