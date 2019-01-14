package com.projectidea.shourov.azmitravels;

/**
 * Created by Shourov on 14,January,2019
 */
public class Upload {
    private String mName;
    private String mImageUrl;
    private String mPersonalPackage;
    private String mGroupPackage;
    private String mPersonalPrice;
    private String mGroupPrice;
    private String mVisaPackage;
    private String mVisaPrice;

    public Upload() {
        //firebase needed
    }

    public Upload(String name, String imageUrl,
                  String personalPackage, String groupPackage,
                  String personalPrice, String groupPrice,
                  String visaPackage, String visaPrice) {
        mName = name;
        mImageUrl = imageUrl;
        mPersonalPackage = personalPackage;
        mGroupPackage = groupPackage;
        mPersonalPrice = personalPrice;
        mGroupPrice = groupPrice;
        mVisaPackage = visaPackage;
        mVisaPrice = visaPrice;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getPersonalPackage() {
        return mPersonalPackage;
    }

    public void setPersonalPackage(String personalPackage) {
        mPersonalPackage = personalPackage;
    }

    public String getGroupPackage() {
        return mGroupPackage;
    }

    public void setGroupPackage(String groupPackage) {
        mGroupPackage = groupPackage;
    }

    public String getPersonalPrice() {
        return mPersonalPrice;
    }

    public void setPersonalPrice(String personalPrice) {
        mPersonalPrice = personalPrice;
    }

    public String getGroupPrice() {
        return mGroupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        mGroupPrice = groupPrice;
    }


    public String getVisaPackage() {
        return mVisaPackage;
    }

    public void setVisaPackage(String visaPackage) {
        mVisaPackage = visaPackage;
    }

    public String getVisaPrice() {
        return mVisaPrice;
    }

    public void setVisaPrice(String visaPrice) {
        mVisaPrice = visaPrice;
    }
}
