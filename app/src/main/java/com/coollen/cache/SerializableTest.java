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

public class SerializableTest {
    public static void test(Context context) {
        TestUser user = new TestUser();
        user.mName = "郭俊焱";
        user.mAge = 33;
        user.mSchool = "Yilong High School";
        user.mMoney = 99999999999999999999999999999.0f;
        TestServer server = new TestServer();
        server.mHostName = "coollen.cn";
        server.mPort = 80;
        try {
            server.mUrl = new URL("http://coollen.cn/queryvideo");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        server.mOtherInfo = new Bundle();
//        server.mOtherInfo.putString("owner", "郭俊焱");
//        server.mOtherInfo.putStringArray("user", new String[]{"sekarao", "feihe"});

        File saveDir = context.getExternalCacheDir();
        if (saveDir != null)
        {
            if (!saveDir.exists())
                saveDir.mkdirs();
            File saveFile = new File(saveDir, "varcache.txt");
            if (!saveFile.exists()) {
                try {
                    saveFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(saveFile);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(user);
                oos.writeObject(server);
                oos.writeObject(new EndMark());
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            readFromFile(saveFile);
        }
    }

    private static void readFromFile(File saveFile)
    {
        ArrayList<Object> listFromFile = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(saveFile);
            ois = new ObjectInputStream(fis);
            Object obj = null;
            while (true)
            {
                obj = ois.readObject();
                if (obj instanceof EndMark)
                    break;
                listFromFile.add(obj);
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
