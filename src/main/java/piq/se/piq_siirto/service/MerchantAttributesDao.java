package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piq.se.piq_siirto.model.MerchantAttributes;

@Repository
public interface MerchantAttributesDao extends JpaRepository<MerchantAttributes, Integer> {
}
