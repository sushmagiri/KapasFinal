package com.wlit.fellowship.kapas;

import java.io.Serializable;

/**
 * Created by user on 3/5/2017.
 */

public class Details implements Serializable {
    private String imageUrl;
    private String name;



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
