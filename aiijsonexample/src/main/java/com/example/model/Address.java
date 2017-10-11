package com.example.model;

import com.aiitec.openapi.json.annotation.JSONField;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Anthony
 * @version 1.0
 * createTime 2017/10/11.
 */

public class Address extends Entity{


    /**邮政编码*/
    private String postcode;
//    /** 默认0 否  1 是*/
//	private String type;
    /**D字应该是小写, 由于java对这个字段有定义，冲突了，所以用大写D*/
    @JSONField(name="default")
    private String Default;

    private String path;


    /**地区id*/
    private int regionId ;
    /**街道*/
    private String street;
    /**地区信息对象*/
    @JSONField(name="regionInfo", entityName="region")
    private List<Region> regionInfo ;




    /**经度*/
    private String longitude;

    /**纬度*/
    private String latitude;
    private String mobile ;
    private String telephone  ;
    private int selected = -1;


    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public int getSelected() {
        return selected;
    }
    public void setSelected(int selected) {
        this.selected = selected;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        DecimalFormat format = new DecimalFormat("#.######");
        try {
            this.longitude = format.format(Double.valueOf(longitude));
        } catch (Exception e) {
        }
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        DecimalFormat format = new DecimalFormat("#.######");
        try {
            this.latitude = format.format(Double.valueOf(latitude));
        } catch (Exception e) {
        }
    }
    /**
     * 获取联系人姓名
     * @return 姓名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置联系人姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取联系人手机号码
     * @return 联系人手机号码
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * 设置联系人手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getDefault() {
        return Default;
    }
    public void setDefault(String default1) {
        Default = default1;
    }
    /**
     * 获取街道地址
     * @return 街道地址
     */
    public String getStreet() {
        return street;
    }
    /**
     * 设置街道地址
     * @param street 街道地址
     */
    public void setStreet(String street) {
        this.street = street;
    }
    /**
     * 获取地区id
     * @return 地区regionId
     */
    public int getRegionId() {
        return regionId;
    }
    /**
     * 设置地区id
     * @param regionId 地区id
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    /**
     * 获取地区详细信息
     * @return 地区详细信息
     */
    public List<Region> getRegionInfo() {
        return regionInfo;
    }
    /**
     * 设置地区详细信息
     * @param regionInfo 地区详细信息
     */
    public void setRegionInfo(List<Region> regionInfo) {
        this.regionInfo = regionInfo;
    }


}