package com.coollen.cache;

import java.io.Serializable;
import java.net.URL;

class TestServer implements Serializable{
    //注意定义此字段
    public static final long serialVersionUID = 1;

    public String mHostName;
    public URL mUrl;
    public int mPort;
    // public Bundle mOtherInfo;
}
