package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import piq.se.piq_siirto.model.User;

public interface UserDao extends JpaRepository<User, String> {
}
