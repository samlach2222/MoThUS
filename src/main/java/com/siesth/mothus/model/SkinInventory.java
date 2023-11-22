package com.siesth.mothus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Entity
public class SkinInventory {



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idSkinInventory;

    @ManyToMany
    Set<Skin> skinList;

    int currentElementSkinId;

    int currentPageSkinId;

    public int getIdSkinInventory() {
        return idSkinInventory;
    }

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

    public void addSkinToList(Skin newSkin){
        skinList.add(newSkin);
    }

    public List<Skin> getSkinList() {
        return skinList;
    }
}
