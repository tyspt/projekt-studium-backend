package de.oth.regensburg.projektstudium.backend;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.PackageType;
import de.oth.regensburg.projektstudium.backend.entity.Person;
import de.oth.regensburg.projektstudium.backend.repository.BuildingRepository;
import de.oth.regensburg.projektstudium.backend.repository.PackageRepository;
import de.oth.regensburg.projektstudium.backend.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    CommandLineRunner initDatabase(BuildingRepository buildingRepository, PersonRepository personRepository, PackageRepository packageRepository) {
        return args -> {
            Building f36 = new Building("F36", "Forschungszentrum", "Bla bla", "Demostraße 42, Regensburg");
            Building d47 = new Building("D47", "Spaßgebaude", "Bla bla", "Spaßstraße 39a, Regensburg");
            log.info("Preloading " + buildingRepository.save(f36));
            log.info("Preloading " + buildingRepository.save(d47));

            Person secretary = new Person("Lisa Sekretärin", "lisa.sekretaerin@demo.de", "333333", f36, "Demostraße 42, F36/2/8, Regensburg");
            Person maxMustermann = new Person("Max Mustermann", "max.mustermann@demo.de", "11111111", f36, "Demostraße 42, F36/2/8, Regensburg");
            Person annamMusterfrau = new Person("Annam Musterfrau", "anna.musterfrau@demo.de", "22222222", d47, "Spaßstraße 39a, D47/6/3, Regensburg");
            log.info("Preloading " + personRepository.save(secretary));
            log.info("Preloading " + personRepository.save(maxMustermann));
            log.info("Preloading " + personRepository.save(annamMusterfrau));

            maxMustermann.setRepresentative(secretary);
            personRepository.save(maxMustermann);

            Package p1 = new Package(PackageType.INBOUND, "12121212121212", "SAP189271931", maxMustermann, annamMusterfrau);
            Package p2 = new Package(PackageType.OUTBOUND, "34343434343434", "SAP9999912313", annamMusterfrau, maxMustermann);
            log.info("Preloading " + packageRepository.save(p1));
            log.info("Preloading " + packageRepository.save(p2));
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
