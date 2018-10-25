package piq.se.piq_siirto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import piq.se.piq_siirto.service.IntegrationService;

@SpringBootApplication
public class PiqSiirtoApplication implements CommandLineRunner {

    @Autowired
    IntegrationService integrationService;

    public static void main(String[] args) {
        SpringApplication.run(PiqSiirtoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        integrationService.initialRepository();
    }
}
