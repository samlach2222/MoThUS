package com.siesth.mothus.repository;

import com.siesth.mothus.model.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationCodeRepository extends JpaRepository<ValidationCode, Long> {
    long deleteByIdValidationCode(int idValidationCode);
}
