/**
 * @file Skin.java
 * @brief Class to create a skin
 * @date 2023-11-21
 * @version 1.0
 */
package com.siesth.mothus.model;

import jakarta.persistence.*;

/**
 * Class to create a skin
 */
@Entity
public class Skin {

    /**
     * ID of the skin
     */
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

    /**
     * CSS file of the skin
     */
    String cssFile;

    /**
     * Constructor of the skin
     * @param rarity rarity of the skin
     * @param type type of the skin
     * @param cssFile CSS file of the skin
     */
    public Skin(SkinRarity rarity, SkinType type, String cssFile){
        super();
        this.setRarity(rarity);
        this.setType(type);
        this.setCssFile(cssFile);
    }

    /**
     * Empty constructor of the skin
     */
    public Skin() {

    }

    /**
     * Getter of the skin ID
     * @return ID of the skin
     */
    public int getIdSkin() {
        return idSkin;
    }

    /**
     * Getter of the skin type
     * @return type of the skin
     */
    public SkinType getType() {
        return type;
    }

    /**
     * Setter of the skin type
     * @param type type of the skin
     */
    public void setType(SkinType type) {
        this.type = type;
    }

    /**
     * Getter of the skin rarity
     * @return rarity of the skin
     */
    public SkinRarity getRarity() {
        return rarity;
    }

    /**
     * Setter of the skin rarity
     * @param rarity rarity of the skin
     */
    public void setRarity(SkinRarity rarity) {
        this.rarity = rarity;
    }

    /**
     * Getter of the skin CSS file
     * @return CSS file of the skin
     */
    public String getCssFile() {
        return cssFile;
    }

    /**
     * Setter of the skin CSS file
     * @param cssFile CSS file of the skin
     */
    public void setCssFile(String cssFile) {
        this.cssFile = cssFile;
    }
}
