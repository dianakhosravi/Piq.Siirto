package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import piq.se.piq_siirto.model.MerchantAttributes;

public interface MerchantAttributesDao extends JpaRepository<MerchantAttributes, Integer> {
}
