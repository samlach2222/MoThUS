/**
 * @file SkinInventory.java
 * @brief Class to manage all the skins of the user
 * @date 2023-11-21
 * @version 1.0
 */
package com.siesth.mothus.model;

import jakarta.persistence.*;
import java.util.*;

/**
 * Class to manage all the skins of the user
 */
public class SkinInventory {

    /**
     * ID of the skin inventory
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idSkinInventory;

    /**
     * List of all the skins of the user
     */
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "skin_skinInventory",
            joinColumns = @JoinColumn(name = "idSkin"),
            inverseJoinColumns = @JoinColumn(name = "idSkinInventory")
    )
    ArrayList<Skin> skinList;

    /**
     * User of the skin inventory
     */
    @OneToOne(mappedBy = "user")
    User user;

    /**
     * ID of the current element skin
     */
    int currentElementSkinId;

    /**
     * ID of the current page skin
     */
    int currentPageSkinId;

    /**
     * Empty constructor of the skin inventory
     */
    public SkinInventory(){
        skinList = new ArrayList<>();
        this.currentElementSkinId = -1;
        this.currentPageSkinId = -1;
    }

    /**
     * Constructor of the skin inventory
     * @param user user of the skin inventory
     */
    public SkinInventory(User user){
        super();
        this.setUser(user);
    }

    /**
     * Getter of the user
     * @return user of the skin inventory
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of the user
     * @param user user of the skin inventory
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of the skin inventory ID
     * @return ID of the skin inventory
     */
    public int getIdSkinInventory() {
        return idSkinInventory;
    }

    /**
     * Getter of the current element skin ID
     * @return ID of the current element skin
     */
    public int getCurrentElementSkinId() {
        return currentElementSkinId;
    }

    /**
     * Setter of the current element skin ID
     * @param currentElementSkinId ID of the current element skin
     */
    public void setCurrentElementSkinId(int currentElementSkinId) {
        this.currentElementSkinId = currentElementSkinId;
    }

    /**
     * Getter of the current page skin ID
     * @return ID of the current page skin
     */
    public int getCurrentPageSkinId() {
        return currentPageSkinId;
    }

    /**
     * Setter of the current page skin ID
     * @param currentPageSkinId ID of the current page skin
     */
    public void setCurrentPageSkinId(int currentPageSkinId) {
        this.currentPageSkinId = currentPageSkinId;
    }

    /**
     * Add a skin to the skin inventory
     * @param newSkin skin to add
     */
    public void addSkinToList(Skin newSkin){
        skinList.add(newSkin);
    }

    /**
     * Get a list of all the skins of the user
     * @return list of all the skins of the user
     */
    public List<Skin> getSkinList() {
        return skinList;
    }
}
