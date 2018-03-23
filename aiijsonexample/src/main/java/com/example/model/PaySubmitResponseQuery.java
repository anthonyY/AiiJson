package com.example.model;

/**
 * @author Anthony
 * @version 1.0
 *          createTime 2018/3/15.
 */

public class PaySubmitResponseQuery extends Entity {

    Double amount;
    String d;
    long id;
    String orderSn;
    String s;
    String t;
    String wxPay;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getWxPay() {
        return wxPay;
    }

    public void setWxPay(String wxPay) {
        this.wxPay = wxPay;
    }
}
