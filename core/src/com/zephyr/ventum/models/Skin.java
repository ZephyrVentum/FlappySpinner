package com.zephyr.ventum.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by sashaklimenko on 7/27/17.
 */

public class Skin {

    private String name;
    private String label;
    private TextureRegion textureRegion;
    private int price;

    public Skin(String name, String label, TextureRegion textureRegion, int price) {
        this.name = name;
        this.label = label;
        this.textureRegion = textureRegion;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
