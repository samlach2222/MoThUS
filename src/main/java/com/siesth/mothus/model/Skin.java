package com.siesth.mothus.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Skin {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idSkin;
    SkinType type;

    SkinRarity rarity;

    String cssFile;

    @ManyToMany
    Set<SkinInventory> users;

    public int getIdSkin() {
        return idSkin;
    }

    public SkinType getType() {
        return type;
    }

    public void setType(SkinType type) {
        this.type = type;
    }

    public SkinRarity getRarity() {
        return rarity;
    }

    public void setRarity(SkinRarity rarity) {
        this.rarity = rarity;
    }

    public String getCssFile() {
        return cssFile;
    }

    public void setCssFile(String cssFile) {
        this.cssFile = cssFile;
    }


}
