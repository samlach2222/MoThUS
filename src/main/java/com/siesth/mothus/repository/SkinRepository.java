package com.siesth.mothus.repository;

import com.siesth.mothus.model.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Skin model
 */
@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
    Skin findSkinByIdSkin(int idSkin);
}
