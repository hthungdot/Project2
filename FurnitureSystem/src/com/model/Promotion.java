package com.model;

import java.sql.Date;


public class Promotion {

    private String promotionID;
    private String promotionName;
    private Date promotionDate;
    private float discount;
    private int amountOfMoney;
    private int quantity;
    private String note;

    public Promotion() {
    }

    public Promotion(String promotionID, String promotionName, Date promotionDate, float discount, int amountOfMoney, int quantity, String note) {
        this.promotionID = promotionID;
        this.promotionName = promotionName;
        this.promotionDate = promotionDate;
        this.discount = discount;
        this.amountOfMoney = amountOfMoney;
        this.quantity = quantity;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Date getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(Date promotionDate) {
        this.promotionDate = promotionDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
