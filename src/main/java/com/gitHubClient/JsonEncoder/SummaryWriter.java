package com.gitHubClient.JsonEncoder;

import com.gitHubClient.issue.Issue;
import com.gitHubClient.issue.Summary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class SummaryWriter implements com.gitHubClient.JsonEncoder.JsonWriter<Summary> {

    String dateFormat = "yyyy-MM-dd'T'hh:mm:ssz";
    boolean prettify =false;

    Gson gson;

    public SummaryWriter() {
        setGson();
    }

    public SummaryWriter(String dateFormat, boolean prettify) {
        this.dateFormat = dateFormat;
        this.prettify = prettify;
        setGson();
    }

    public SummaryWriter(boolean prettify) {
        this.prettify = prettify;
        setGson();
    }

    private void setGson(){
        GsonBuilder gsonB = new GsonBuilder().setDateFormat(dateFormat);
        if (prettify)  gsonB.setPrettyPrinting();
        gson= gsonB.create();
    }

    @Override
    public String toJson(Summary obj) {
        return gson.toJson(obj);
    }

    /**
     *
     * @param os An open outputstream for outputting JSON. Note: It's the responsibility of the caller to close the stream when done with all piping
     * @param obj The summary object that requires JSON conversion
     */
    @Override
    public void streamWrite(OutputStream os, Summary obj) {
        JsonWriter writer;
        writer = new JsonWriter(new OutputStreamWriter(os));
        try {
            toJsonStream(writer, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toJsonStream(com.google.gson.stream.JsonWriter w, Summary s) throws IOException {
        w.beginObject();
        w.name("issues");
        w.beginArray();
        for (Issue i: s.getIssues()){
            w.value(gson.toJson(i));
        }
        w.endArray();
        w.name("top_day");
        w.value(gson.toJson(s.getTop_day()));
        w.endObject();
        w.flush();
    }
}
