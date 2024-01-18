package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.Skin;
import com.siesth.mothus.model.SkinRarity;
import com.siesth.mothus.model.SkinType;
import com.siesth.mothus.repository.SkinRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to manage the skin management.
 */
@Service
public class SkinManagement {
    /**
     * This field is used to get the skin repository.
     */
    @Autowired
    SkinRepository skinRepository;

    /**
     * This method is used to initialize the skins when the server will be launching.
     */
    @PostConstruct
    public void initSkins() {
        List<Skin> skins = skinRepository.findAll();
        if (skins.isEmpty()) {
            // ELEMENT SKINS
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/gray.css", "/assets/skins/elementSkins/image/gray.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/greenBlue.css", "/assets/skins/elementSkins/image/greenBlue.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/darkRed.css", "/assets/skins/elementSkins/image/darkRed.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/mustard.css", "/assets/skins/elementSkins/image/mustard.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/lightRed.css", "/assets/skins/elementSkins/image/lightRed.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.ElementSkin, "/assets/skins/elementSkins/css/green.css", "/assets/skins/elementSkins/image/green.png"));
            skinRepository.save(new Skin(SkinRarity.Uncommon, SkinType.ElementSkin, "/assets/skins/elementSkins/css/purple.css", "/assets/skins/elementSkins/image/purple.png"));
            skinRepository.save(new Skin(SkinRarity.Rare, SkinType.ElementSkin, "/assets/skins/elementSkins/css/pinkPurpleGradientBottom.css", "/assets/skins/elementSkins/image/pinkPurpleGradientBottom.png"));
            skinRepository.save(new Skin(SkinRarity.Rare, SkinType.ElementSkin, "/assets/skins/elementSkins/css/pinkPurpleGradientMiddle.css", "/assets/skins/elementSkins/image/pinkPurpleGradientMiddle.png"));
            skinRepository.save(new Skin(SkinRarity.Mythic, SkinType.ElementSkin, "/assets/skins/elementSkins/css/pinkBlueGradient.css", "/assets/skins/elementSkins/image/pinkBlueGradient.png"));
            skinRepository.save(new Skin(SkinRarity.Mythic, SkinType.ElementSkin, "/assets/skins/elementSkins/css/schoolElementTable.css", "/assets/skins/elementSkins/image/schoolElementTable.png"));

            // PAGE SKINS
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.PageSkin, "/assets/skins/pageSkins/css/light.css", "/assets/skins/pageSkins/image/light.png"));
            skinRepository.save(new Skin(SkinRarity.Common, SkinType.PageSkin, "/assets/skins/pageSkins/css/dark.css", "/assets/skins/pageSkins/image/dark.png"));
            skinRepository.save(new Skin(SkinRarity.Uncommon, SkinType.PageSkin, "/assets/skins/pageSkins/css/gradientPinkBlue.css", "/assets/skins/pageSkins/image/gradientPinkBlue.png"));
            skinRepository.save(new Skin(SkinRarity.Rare, SkinType.PageSkin, "/assets/skins/pageSkins/css/blueChemistry.css", "/assets/skins/pageSkins/image/blueChemistry.png"));
            skinRepository.save(new Skin(SkinRarity.Rare, SkinType.PageSkin, "/assets/skins/pageSkins/css/blueTriangle.css", "/assets/skins/pageSkins/image/blueTriangle.png"));
            skinRepository.save(new Skin(SkinRarity.Rare, SkinType.PageSkin, "/assets/skins/pageSkins/css/pinkChemistry.css", "/assets/skins/pageSkins/image/pinkChemistry.png"));
            skinRepository.save(new Skin(SkinRarity.Mythic, SkinType.PageSkin, "/assets/skins/pageSkins/css/animatedMountain.css", "/assets/skins/pageSkins/image/animatedMountain.gif"));
        }
    }
}
