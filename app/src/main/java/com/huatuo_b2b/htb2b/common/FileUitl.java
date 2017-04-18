package com.huatuo_b2b.htb2b.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

public class FileUitl {
    private static final String TAG = "FileUitl";


    // 清空文件内容
    public static final void clearFileContent(String path) {
        File f = new File(path);
        if (f.exists() && f.isFile()) {
            try {
                FileWriter wf = new FileWriter(f);
                wf.write("");
                wf.flush();
                wf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
