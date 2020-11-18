package de.oth.regensburg.projektstudium.backend;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import de.oth.regensburg.projektstudium.backend.service.BuildingService;
import de.oth.regensburg.projektstudium.backend.service.DriverService;
import de.oth.regensburg.projektstudium.backend.service.EmployeeService;
import de.oth.regensburg.projektstudium.backend.service.PackageService;
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

@Configuration
public class Config extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    LoggerInterceptor logInterceptor;

    public Config(LoggerInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Bean
    CommandLineRunner initDatabase(
            BuildingService buildingService,
            EmployeeService employeeService,
            PackageService packageService,
            DriverService driverService
    ) {
        return args -> {
            Building f36 = new Building("F36", "Forschungszentrum", "Dies ist ein Demogebäude.", "Dummystraße 19, Regensburg");
            Building u22 = new Building("H47", "Sammelgebäude", "Hintergrund: Keilberg mit der Steinbruchkante, Kirchturm von St. Anton, Zuckerfabrik.", "Universitätsstraße 31, 93053 Regensburg");
            log.info("Preloading " + buildingService.createBuilding(f36));
            log.info("Preloading " + buildingService.createBuilding(u22));

            Employee lisaSekretaerin = new Employee("Lisa Sekretärin", "lisa.sekretaerin@demo.de", "0134/994474454", f36, "Dummystraße 19, F36/2/8, Regensburg");
            Employee maxMustermann = new Employee("Max Mustermann", "max.mustermann@demo.de", "0158/61664165", f36, "Dummystraße 19, F36/2/6, Regensburg");
            Employee annamMusterfrau = new Employee("Anna Musterfrau", "anna.musterfrau@demo.de", "0187/165243152", u22, "Universitätsstraße 31, U22/6/3, Regensburg");
            Employee tomCruise = new Employee("Tom Cruise", "tom.cruise@demo.de", "0189/164642543", u22, "Universitätsstraße 31, U22/3/1, Regensburg");
            log.info("Preloading " + employeeService.createEmployee(lisaSekretaerin));
            log.info("Preloading " + employeeService.createEmployee(maxMustermann));
            log.info("Preloading " + employeeService.createEmployee(annamMusterfrau));
            log.info("Preloading " + employeeService.createEmployee(tomCruise));

            maxMustermann.setRepresentative(lisaSekretaerin);
            employeeService.updateEmployee(maxMustermann);

            tomCruise.setRepresentative(annamMusterfrau);
            employeeService.updateEmployee(tomCruise);

//            Random random = new Random();
//            String[] inboundStatusOptions = {"CREATED"};
//            p1.setStatus(PackageStatus.valueOf(inboundStatusOptions[random.nextInt(inboundStatusOptions.length)]));

            Package p1 = new Package(PackageType.INBOUND, "4337185786433", "SAP189271931", maxMustermann, annamMusterfrau);
            p1.setStatus(PackageStatus.CREATED);
            log.info("Preloading " + packageService.createPackage(p1));

            Package p2 = new Package(PackageType.INBOUND, "73203136132591", "SAP189271931", tomCruise, lisaSekretaerin);
            p1.setStatus(PackageStatus.CREATED);
            log.info("Preloading " + packageService.createPackage(p2));

//            Package p2 = new Package(PackageType.OUTBOUND, Integer.toString(random.nextInt(999999999)), "SAP9999912313", annamMusterfrau, maxMustermann);
//            String[] outboundStatusOptions = {"CREATED"};
//            p2.setStatus(PackageStatus.valueOf(outboundStatusOptions[random.nextInt(outboundStatusOptions.length)]));
//            log.info("Preloading " + packageRepository.save(p2));

            Driver d1 = new Driver("Demo Driver 1", "driver1@demo.de", "d123456789", "Best Logistics Ltd.");
            log.info("Preloading " + driverService.createDriver(d1));
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
