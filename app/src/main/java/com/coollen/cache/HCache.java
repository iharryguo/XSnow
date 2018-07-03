package com.coollen.cache;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HCache {
    private static volatile HCache sInstance;
    private static final boolean  DEBUG_MODE = true;
    private static final String CACHE_FOLDER_NAME = "coollenHCache";
    private static final String CACHE_COMMON_FILE_NAME = "common.txt";

    private HashMap<String, Object> mCacheMap = new HashMap<>();
    private File mCacheFile;

    private HCache() {}

    public static HCache getInstance() {
        if (sInstance == null) {
            synchronized (HCache.class) {
                if (sInstance == null)
                    sInstance = new HCache();
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        // 1. 获取cache文件路径
        File cacheDir = null;
        if (DEBUG_MODE)
            cacheDir = context.getExternalCacheDir();
        else
            cacheDir = context.getCacheDir();

        if (cacheDir != null) {
            init(cacheDir.getAbsolutePath() + File.separator + CACHE_FOLDER_NAME);
        }
    }

    public void init(String cacheFilePath) {
        // 1. 确保目录存在
        File cacheDir = new File(cacheFilePath);
        if (!cacheDir.exists())
            cacheDir.mkdirs();

        if (cacheDir.exists()) {
            mCacheFile = new File(cacheDir, CACHE_COMMON_FILE_NAME);
            if (!mCacheFile.exists()) {
                boolean createFileSuccess = false;
                try {
                    createFileSuccess = mCacheFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!createFileSuccess)
                    mCacheFile = null;
            }
            else {
                // 从文件中读取数据到cache中
                FileInputStream fis = null;
                ObjectInputStream ois = null;
                try {
                    fis = new FileInputStream(mCacheFile);
                    ois = new ObjectInputStream(fis);
                    while (true)
                    {
                        Object obj = ois.readObject();
                        if (obj instanceof EndMark)
                            break;
                        else if (obj instanceof String) {
                            Object obj2 = ois.readObject();
                            if (obj2 instanceof EndMark)
                                break;
                            else
                                mCacheMap.put((String)obj, obj2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (ois != null) {
                        try {
                            ois.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public Object get(String key) {
        return mCacheMap.get(key);
    }

    public <T> T get(String key, T defaultValue) {
        Object value = mCacheMap.get(key);
        if (value instanceof T) {
            return ((T) value);
        }
        else
            return defaultValue;
    }

    public String getString(String key) {
        Object value = mCacheMap.get(key);
        if (value instanceof String)
    }
}
