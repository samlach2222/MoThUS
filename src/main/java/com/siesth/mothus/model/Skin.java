/**
 * @file Skin.java
 * @brief Class to create a skin
 * @date 2023-11-24
 * @version 1.0
 */
package com.siesth.mothus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Class to create a skin
 */
@Entity
public class Skin {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idSkin;

    /**
     * Type of the skin (PageSkin or ElementSkin)
     */
    SkinType type;

    /**
     * Rarity of the skin (Common, Uncommon, Rare, Mythic)
     */
    SkinRarity rarity;

    String cssFile;

    String previewImage;

    /**
     * Constructor of the skin
     * @param rarity rarity of the skin
     * @param type type of the skin
     * @param cssFile CSS file of the skin
     */
    public Skin(SkinRarity rarity, SkinType type, String cssFile, String previewImage){
        super();
        this.setRarity(rarity);
        this.setType(type);
        this.setCssFile(cssFile);
        this.setPreviewImage(previewImage);
    }

    public Skin() {

    }

    public int getIdSkin() {
        return idSkin;
    }

    /**
     * Getter of the skin type
     * @return type of the skin (PageSkin or ElementSkin)
     */
    public SkinType getType() {
        return type;
    }

    /**
     * Setter of the skin type
     * @param type type of the skin (PageSkin or ElementSkin)
     */
    public void setType(SkinType type) {
        this.type = type;
    }

    /**
     * Getter of the skin rarity
     * @return rarity of the skin (Common, Uncommon, Rare, Mythic)
     */
    public SkinRarity getRarity() {
        return rarity;
    }

    /**
     * Setter of the skin rarity
     * @param rarity rarity of the skin (Common, Uncommon, Rare, Mythic)
     */
    public void setRarity(SkinRarity rarity) {
        this.rarity = rarity;
    }

    public String getCssFile() {
        return cssFile;
    }

    public void setCssFile(String cssFile) {
        this.cssFile = cssFile;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }
}
