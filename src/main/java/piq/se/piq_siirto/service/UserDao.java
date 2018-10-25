package piq.se.piq_siirto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piq.se.piq_siirto.model.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

}
