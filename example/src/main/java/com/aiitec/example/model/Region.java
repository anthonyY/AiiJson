package com.aiitec.example.model;

/**
 * regionInfo 的 region对象
 * @author Anthony
 * @version 1.0
 * createTime 2017/10/11.
 */
public class Region extends Entity{

    protected long id = -1;
    /**父id*/
    protected long parentId = -1;
    /**拼音*/
    protected String pinyin;

    protected int deep = -1;

    protected int status = -1;
//	/**是否是热门城市*/
//    protected int isHot = -1;
//	/**是否是最近（住过的）城市*/
//    protected int isHistory = -1;

    //    protected String timestamp_update;
    protected String timestamp;

    public int getDeep() {
        return deep;
    }
    public void setDeep(int deep) {
        this.deep = deep;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
//	public String getTimestamp_update() {
//		return timestamp_update;
//	}
//	public void setTimestamp_update(String timestamp_update) {
//		this.timestamp_update = timestamp_update;
//	}
    /**
     * 获取父Id
     * @return 父id
     */
    public long getParentId() {
        return parentId;
    }
    /**
     * 设置父id
     * @param parentId 父id
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    /**
     * 获取拼音
     * @return 拼音
     */
    public String getPinyin() {
        return pinyin;
    }
    /**
     * 设置拼音
     * @param pinyin 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    //    public int getIsHot() {
//        return isHot;
//    }
//    public void setIsHot(int isHot) {
//        this.isHot = isHot;
//    }
//    public int getIsHistory() {
//        return isHistory;
//    }
//    public void setIsHistory(int isHistory) {
//        this.isHistory = isHistory;
//    }
    @Override
    public long getId() {
        return id;
    }
    @Override
    public void setId(long id) {
        this.id = id;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}