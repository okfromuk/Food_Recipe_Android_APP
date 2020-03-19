package com.example.newstart;

import android.widget.EditText;

class Upload {

    private String imgName;
    private String imgUrl;
    public String despriptionnnn;
    private String ingredientsssss;
    public Upload() {
    }

    public Upload(String imgName, String despriptionnnn,String ingredientsssss,String imgUrl) {
        if(imgName.trim().equals(""))
        {


        }

        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.despriptionnnn= despriptionnnn;
        this.ingredientsssss= ingredientsssss;
    }


    public String getImgName() {
        return imgName;
    }


    public void setImgName(String imgName) {
        this.imgName = imgName;
    }



    public String getDespriptionnnn() {

        return despriptionnnn;
    }
    public void setDespriptionnnn(String despriptionnnn) {
        this.despriptionnnn = despriptionnnn;
    }
    public String getIngredientsssss() {
        return ingredientsssss;
    }


    public void setIngredientsssss(String ingredientsssss) {
        this.ingredientsssss = ingredientsssss;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}

