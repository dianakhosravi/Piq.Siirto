package piq.se.piq_siirto.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import piq.se.piq_siirto.model.Transaction;

public interface TransactionDao extends JpaRepository<Transaction, String> {
}
