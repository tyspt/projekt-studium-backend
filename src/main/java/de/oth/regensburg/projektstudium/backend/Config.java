package de.oth.regensburg.projektstudium.backend;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import de.oth.regensburg.projektstudium.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    LoggerInterceptor logInterceptor;

    public Config(LoggerInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Bean
    CommandLineRunner initDatabase(
            BuildingRepository buildingRepository,
            EmployeeRepository employeeRepository,
            PackageRepository packageRepository,
            HandoverRepository handoverRepository,
            DriverRepository driverRepository
    ) {
        return args -> {
            Building f36 = new Building("F36", "Forschungszentrum", "Bla bla", "Demostraße 42, Regensburg");
            Building d47 = new Building("D47", "Spaßgebaude", "Bla bla", "Spaßstraße 39a, Regensburg");
            log.info("Preloading " + buildingRepository.save(f36));
            log.info("Preloading " + buildingRepository.save(d47));

            Employee secretary = new Employee("Lisa Sekretärin", "lisa.sekretaerin@demo.de", "333333", f36, "Demostraße 42, F36/2/8, Regensburg");
            Employee maxMustermann = new Employee("Max Mustermann", "max.mustermann@demo.de", "11111111", f36, "Demostraße 42, F36/2/8, Regensburg");
            Employee annamMusterfrau = new Employee("Anna Musterfrau", "anna.musterfrau@demo.de", "22222222", d47, "Spaßstraße 39a, D47/6/3, Regensburg");
            log.info("Preloading " + employeeRepository.save(secretary));
            log.info("Preloading " + employeeRepository.save(maxMustermann));
            log.info("Preloading " + employeeRepository.save(annamMusterfrau));

            maxMustermann.setRepresentative(secretary);
            employeeRepository.save(maxMustermann);

            Random random = new Random();

            for (int i = 0; i < 5; i++) {
                Package p1 = new Package(PackageType.INBOUND, Integer.toString(random.nextInt(999999999)), "SAP189271931", maxMustermann, annamMusterfrau);
                Package p2 = new Package(PackageType.OUTBOUND, Integer.toString(random.nextInt(999999999)), "SAP9999912313", annamMusterfrau, maxMustermann);

                String[] inboundStatusOptions = {"CREATED"};
                String[] outboundStatusOptions = {"CREATED"};

                p1.setStatus(PackageStatus.valueOf(inboundStatusOptions[random.nextInt(inboundStatusOptions.length)]));
                p2.setStatus(PackageStatus.valueOf(outboundStatusOptions[random.nextInt(outboundStatusOptions.length)]));
                log.info("Preloading " + packageRepository.save(p1));
                log.info("Preloading " + packageRepository.save(p2));
            }

//            Handover h1 = new Handover(UUID.randomUUID());
//            h1.addPackage(p1);
//            h1.addPackage(p2);
//            handoverRepository.save(h1);
//            packageRepository.save(p1);
//            packageRepository.save(p2);

            Driver d1 = new Driver("Demo Driver 1", "driver1@demo.de", "d123456789", "Best Logistics Ltd.");
            log.info("Preloading " + driverRepository.save(d1));
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // TODO: Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }
}
