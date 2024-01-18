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

    /**
     * Id of the skin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idSkin;

    /**
     * Type of the skin (PageSkin or ElementSkin)
     */
    SkinType type;

    /**
     * Rarity of the skin (Common, Uncommon, Rare, Mythic)
     */
    SkinRarity rarity;

    /**
     * CSS file of the skin
     */
    String cssFile;

    /**
     * Preview image of the skin
     */
    String previewImage;

    /**
     * Constructor of the skin
     * @param rarity       rarity of the skin
     * @param type         type of the skin
     * @param cssFile      CSS file of the skin
     * @param previewImage preview image of the skin
     */
    public Skin(SkinRarity rarity, SkinType type, String cssFile, String previewImage) {
        super();
        this.setRarity(rarity);
        this.setType(type);
        this.setCssFile(cssFile);
        this.setPreviewImage(previewImage);
    }

    /**
     * Empty constructor
     */
    public Skin() {

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

    /**
     * Getter of css file
     * @return CSS file of the skin
     */
    public String getCssFile() {
        return cssFile;
    }

    /**
     * Setter of css file
     * @param cssFile CSS file of the skin
     */
    public void setCssFile(String cssFile) {
        this.cssFile = cssFile;
    }

    /**
     * Getter of preview image
     * @return preview image of the skin
     */
    public String getPreviewImage() {
        return previewImage;
    }

    /**
     * Setter of preview image
     * @param previewImage preview image of the skin
     */
    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public int getIdSkin() {
        return idSkin;
    }
}
