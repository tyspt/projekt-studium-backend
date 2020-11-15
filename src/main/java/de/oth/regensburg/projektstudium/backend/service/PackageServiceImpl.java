package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.dto.PackageDTO;
import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import de.oth.regensburg.projektstudium.backend.exceptions.ForbiddenException;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.PackageRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {

    private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);
    private final ModelMapper modelMapper;
    private final PackageRepository packageRepository;
    private final DriverService driverService;

    public PackageServiceImpl(
            ModelMapper modelMapper,
            PackageRepository packageRepository,
            DriverService driverService
    ) {
        this.modelMapper = modelMapper;
        this.packageRepository = packageRepository;
        this.driverService = driverService;
    }

    @Override
    public List<PackageDTO> findAll() {
        return packageRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PackageDTO findPackageDTOByIdOrBarcode(String idOrBarcode) {
        return convertToDto(this.findPackageByIdOrBarcode(idOrBarcode));
    }

    @Override
    public Package findPackageByIdOrBarcode(String idOrBarcode){
        Long id = Long.valueOf(idOrBarcode);
        Optional<Package> resById = packageRepository.findById(id);

        Package pkg = new Package();
        pkg.setBarcode(idOrBarcode);
        Optional<Package> resByBarcode = packageRepository.findOne(Example.of(pkg));

        pkg = resById.orElseGet(() -> resByBarcode
                .orElseThrow(() -> new NotFoundException(Package.class)));
        return pkg;
    }

    @Override
    public PackageDTO addPackage(PackageDTO newPackage) throws ParseException {
        Package pkg = convertToEntity(newPackage);
        return convertToDto(packageRepository.save(pkg));
    }

    @Override
    public PackageDTO collectPackage(String idOrBarcode, Long driverId) {
        final Package dbPackage = this.findPackageByIdOrBarcode(idOrBarcode);
        final Driver driver = this.driverService.findOneById(driverId);

        if (dbPackage.getType() != PackageType.OUTBOUND || dbPackage.getStatus() != PackageStatus.CREATED) {
            throw new ForbiddenException("Package #" + idOrBarcode + " has wrong type or in wrong status: " + dbPackage.getStatus() + " (" + dbPackage.getType().toString().toLowerCase() +
                    "), collect is only allow for outbound packages with CREATED status.");
        }

        dbPackage.setDriver(driver);
        dbPackage.setStatus(PackageStatus.IN_TRANSPORT);
        return convertToDto(packageRepository.save(dbPackage));
    }

    private PackageDTO convertToDto(Package pkg) {
        PackageDTO packageDTO = modelMapper.map(pkg, PackageDTO.class);
        return packageDTO;
    }

    private Package convertToEntity(PackageDTO packageDTO) throws ParseException {
        Package pkg = modelMapper.map(packageDTO, Package.class);
        return pkg;
    }
}
