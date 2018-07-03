package com.coollen.cache;

import java.io.Serializable;

class TestUser implements Serializable {
    //注意定义此字段
    public static final long serialVersionUID = 1;

    public String mName;
    public int mAge;
    public String mSchool;
    public float mMoney;
}
