package com.drofff.manageday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drofff.manageday.entity.RGBColor;

@Repository
public interface RGBColorRepository extends JpaRepository<RGBColor, Long>, OwnableRepository<RGBColor> {

	RGBColor findFirstByIsDefault(boolean isDefault);

}
