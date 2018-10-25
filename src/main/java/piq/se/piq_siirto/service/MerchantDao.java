package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import piq.se.piq_siirto.model.Merchant;

public interface MerchantDao extends JpaRepository<Merchant, String> {
}
