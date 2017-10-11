package com.aiitec.example.model;

/**
 * @author Anthony
 * @version 1.0
 * createTime 2017/10/11.
 */
public class Image extends Entity{

    /**文件名*/
    private String filename;
    /**图片文件夹路径*/
    private String path;
    private String extension;
    private int type = -1;

    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    /**
     * 获取文件名
     * @return 文件名
     */
    public String getFilename() {
        return filename;
    }
    /**
     * 设置文件名
     * @param filename 文件名
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    /**
     * 获取图片文件夹路径
     * @return 文件夹路径
     */
    public String getPath() {
        return path;
    }
    /**
     * 设置图片文件夹路径
     * @param path 文件夹路径
     */
    public void setPath(String path) {
        this.path = path;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}