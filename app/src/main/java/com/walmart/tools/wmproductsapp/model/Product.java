package com.walmart.tools.wmproductsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Selvan Rajan on 8/16/17.
 */
public class Product {
    String itemId;
    String parentItemId;
    String name;
    String msrp;
    String salePrice;
    String shortDescription;
    String longDescription;
    String thumbnailImage;
    String largeImage;
    String gender;

    public Product () {}

    public int describeContents() {
        return 0;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsrp(String msrp) {
        this.msrp = msrp;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getItemId() {
        return itemId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public String getName() {
        return name;
    }

    public String getMsrp() {
        return msrp;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return itemId+" , "+name;
    }



}
