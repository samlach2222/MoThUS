/**
 * @file SkinInventory.java
 * @brief Class to manage all the skins of the user
 * @date 2023-11-25
 * @version 1.0
 */
package com.siesth.mothus.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to manage all the skins of the user
 */
@Entity
public class SkinInventory {

    /**
     * Id of the skin inventory
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idSkinInventory;

    /**
     * List of all the skins of the user
     */
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "skinSkinInventory",
            joinColumns = @JoinColumn(name = "idSkin"),
            inverseJoinColumns = @JoinColumn(name = "idSkinInventory")
    )
    Collection<Skin> skinList;

    /**
     * Id of the current element skin
     */
    int currentElementSkinId;

    public int getCurrentElementSkinId() {
        return currentElementSkinId;
    }

    public void setCurrentElementSkinId(int currentElementSkinId) {
        this.currentElementSkinId = currentElementSkinId;
    }

    public int getCurrentPageSkinId() {
        return currentPageSkinId;
    }

    public void setCurrentPageSkinId(int currentPageSkinId) {
        this.currentPageSkinId = currentPageSkinId;
    }

    /**
     * Id of the current page skin
     */
    int currentPageSkinId;

    /**
     * Constructor of the skin inventory
     */
    public SkinInventory() {
        skinList = new ArrayList<>();
        this.currentElementSkinId = -1;
        this.currentPageSkinId = -1;
    }

    /**
     * Add a skin to the skin inventory
     * @param newSkin skin to add
     */
    public void addSkinToList(Skin newSkin) {
        skinList.add(newSkin);
    }

    /**
     * Get a list of all the skins of the user
     * @return list of all the skins of the user
     */
    public Collection<Skin> getSkinList() {
        return skinList;
    }
}
