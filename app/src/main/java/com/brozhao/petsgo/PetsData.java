package com.brozhao.petsgo;

/**
 * Created by petingo on 2017/7/8.
 */

/*
info[n]:
0 <catchDate>2017-07-03</catchDate>
1 <catchLocation>民眾拾獲</catchLocation>
2 <variety>貴賓</variety>
3 <sex>公</sex>
4 <size>小型</size>
5 <source>民眾捨獲</source>
6 <memo>僅供飼主指認.不予開放領養</memo>
7 <pic> http://dog.chiayi.gov.tw/upload/bigphoto/thumbnail/announcement20170703102106-1.jpg </pic>
8 kind = dog / cat
 */
public class PetsData {
    private String[] info = new String[8];
    PetsData(String[] input){
        this.info = input;
    }
    public void setInfo(String[] input){
        this.info = input;
    }
    public String[] getInfo(){
        return this.info;
    }
    public String getCatchDate(){
        return this.info[0];
    }
    public String getCatchLocation(){
        return this.info[1];
    }
    public String getVariety(){
        return this.info[2];
    }
    public String getSex(){
        return this.info[3];
    }
    public String getSize(){
        return this.info[4];
    }
    public String getSource(){
        return this.info[5];
    }
    public String getMemo(){
        return this.info[6];
    }
    public String getPicURL(){
        return this.info[7];
    }
    public String getKind(){return this.info[8];}
}
