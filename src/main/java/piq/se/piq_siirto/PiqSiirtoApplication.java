package piq.se.piq_siirto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import piq.se.piq_siirto.service.IntegrationService;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

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

    /*public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        MediaType mediaType = new MediaType("application","x-www-form-urlencoded", Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(mediaType));
        converters.add(converter);
        configureMessageConverters(converters);
    }*/
}
