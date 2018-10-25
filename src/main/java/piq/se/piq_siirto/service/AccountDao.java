package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import piq.se.piq_siirto.model.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
}
