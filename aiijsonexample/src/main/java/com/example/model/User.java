package com.example.model;

/**
 * 用户类
 * @author Anthony
 * @version 1.0
 *          createTime 2017/10/11.
 */
public class User extends Entity {

    /**邮箱*/
    private String email;
    /**性别*/
    private int sex = -1;
    /**第三方平台   2.QQ， 3 微博*/
    private int partner = -1;
    /**第三方的id*/
    private String openId ;
    /**头像图片路径*/
    private String imagePath;

    /**手机号码*/
    private String mobile ;

    private String nickname;
    private String pinyin;
    /**添加理由*/
    private String content;
    private String qq;
    private String telephone;
    private String job;

    private int isTop = -1;
    private int isShield = -1;
    private int isDoNotRemind = -1;
    //	@Deprecated
//	private int recommendStat = -1;
    private int statRecommend = -1;
    private double recommendBonus = -1;
    private int recommendBeyond = -1;
    private int recommendRanking = -1;
    private double money = -1;
    private double isBuy = -1;
    private float height;
    /**
     * 0 没有关注；
     *  1 我已关注；
     *  2 对方关注我；
     *  3 相互关注；
     */
    private int attention = -1;
    /**
     * 是否置顶
     * 1 置顶；2 不置顶；
     */
    private int top = -1;
    /**
     * 是否屏蔽人脉圈
     * 1 是；2 否；
     */
    private int shield = -1;

    /**
     * 是否免打扰
     * 1 免打扰；2 正常通信；
     */
    private int disturb = -1;

    private Address address;




    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public double getIsBuy() {
        return isBuy;
    }
    public void setIsBuy(double isBuy) {
        this.isBuy = isBuy;
    }
    public int getStatRecommend() {
        return statRecommend;
    }
    public void setStatRecommend(int statRecommend) {
        this.statRecommend = statRecommend;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getAttention() {
        return attention;
    }
    public void setAttention(int attention) {
        this.attention = attention;
    }
    public int getTop() {
        return top;
    }
    public void setTop(int top) {
        this.top = top;
    }
    public int getShield() {
        return shield;
    }
    public void setShield(int shield) {
        this.shield = shield;
    }
    public int getDisturb() {
        return disturb;
    }
    public void setDisturb(int disturb) {
        this.disturb = disturb;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public int getIsTop() {
        return isTop;
    }
    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }
    public int getIsShield() {
        return isShield;
    }
    public void setIsShield(int isShield) {
        this.isShield = isShield;
    }
    public int getIsDoNotRemind() {
        return isDoNotRemind;
    }
    public void setIsDoNotRemind(int isDoNotRemind) {
        this.isDoNotRemind = isDoNotRemind;
    }
    //	@Deprecated
//	public int getRecommendStat() {
//		return recommendStat;
//	}
//	@Deprecated
//	public void setRecommendStat(int recommendStat) {
//		this.recommendStat = recommendStat;
//	}
    public int getRecommendRanking() {
        return recommendRanking;
    }
    public void setRecommendRanking(int recommendRanking) {
        this.recommendRanking = recommendRanking;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    /**
     * 获取邮箱
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }
    /**
     * 设置邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * 获取用户性别
     * @return 性别
     */
    public int getSex() {
        return sex;
    }
    /**
     * 设置用户性别
     * @param sex 性别
     */
    public void setSex(int sex) {
        this.sex = sex;
    }
    /**
     * 获取头像图片路径
     * @return 图片路径
     */
    public String getImagePath() {
        return imagePath;
    }
    /**
     * 设置用户头像图片路径
     * @param imagePath 图片路径
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    /**
     * 获取手机号码
     * @return 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }
    @Override
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * 获取第三方平台 2.QQ， 3 微博
     * @return 第三方平台 2.QQ， 3 微博
     */
    public int getPartner() {
        return partner;
    }
    /**
     * 设置第三方平台 2.QQ， 3 微博
     * @param partner 第三方平台 2.QQ， 3 微博
     */
    public void setPartner(int partner) {
        this.partner = partner;
    }
    /**
     * 获取第三方平台openId
     * @return 第三方平台openId
     */
    public String getOpenId() {
        return openId;
    }
    /**
     * 设置第三方平台openId
     * @param openId 第三方平台openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public int getRecommendBeyond() {
        return recommendBeyond;
    }
    public void setRecommendBeyond(int recommendBeyond) {
        this.recommendBeyond = recommendBeyond;
    }
    public double getRecommendBonus() {
        return recommendBonus;
    }
    public void setRecommendBonus(double recommendBonus) {
        this.recommendBonus = recommendBonus;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }


}