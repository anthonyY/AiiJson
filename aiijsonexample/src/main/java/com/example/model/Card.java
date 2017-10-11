package com.example.model;

import com.aiitec.openapi.json.annotation.JSONField;

import java.util.ArrayList;

/**
 * @author Anthony
 * @version 1.0
 * createTime 2017/10/11.
 */

public class Card extends Entity {

    private User user;
    private Address address;
    private String cardName;
    private long id ;
    private int type ;
    //	@Deprecated
    @JSONField(notCombination=true)
    private int visitStat = -1;
    private int statVisit = -1;
    private int idImage = -1;
    private int isDevice = -1;
    private int imageId = -1;
    private int deviceId = -1;
    private int weixinImage = -1;

    private long userId = -1;


    private String imagePath;
    private String timestampUpdate;
    private String englishName;
    @JSONField(name="email")
    private ArrayList<String> emails;
    @JSONField(name="qq")
    private ArrayList<String> qqs;
    @JSONField(name="mobile")
    private ArrayList<String> mobiles;

    private String signature;
    private String job;
    private String show;
    private String pinyin;
    private Image wxImage;



    private Image image;

    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public int getWeixinImage() {
        return weixinImage;
    }
    public void setWeixinImage(int weixinImage) {
        this.weixinImage = weixinImage;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public Image getWxImage() {
        return wxImage;
    }
    public void setWxImage(Image wxImage) {
        this.wxImage = wxImage;
    }
    public int getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public int getStatVisit() {
        return statVisit;
    }
    public void setStatVisit(int statVisit) {
        this.statVisit = statVisit;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getTimestampUpdate() {
        return timestampUpdate;
    }
    public void setTimestampUpdate(String timestampUpdate) {
        this.timestampUpdate = timestampUpdate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getEnglishName() {
        return englishName;
    }
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    public ArrayList<String> getEmails() {
        return emails;
    }
    public void setEmail(ArrayList<String> emails) {
        this.emails = emails;
    }
    public ArrayList<String> getQqs() {
        return qqs;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public int getIsDevice() {
        return isDevice;
    }
    public void setIsDevice(int isDevice) {
        this.isDevice = isDevice;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public int getIdImage() {
        return idImage;
    }
    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
    public String getShow() {
        return show;
    }
    public void setShow(String show) {
        this.show = show;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public ArrayList<String> getMobiles() {
        return mobiles;
    }
    public void setMobiles(ArrayList<String> mobiles) {
        this.mobiles = mobiles;
    }
    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }
    public void setQqs(ArrayList<String> qqs) {
        this.qqs = qqs;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
