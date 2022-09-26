package com.usman.qrcodereader.PkQr;

public class Model
{
    String data;
    int isurl,sno;

    public Model(String data, int isurl, int sno) {
        this.data = data;
        this.isurl = isurl;
        this.sno = sno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIsurl() {
        return isurl;
    }

    public void setIsurl(int isurl) {
        this.isurl = isurl;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
}
