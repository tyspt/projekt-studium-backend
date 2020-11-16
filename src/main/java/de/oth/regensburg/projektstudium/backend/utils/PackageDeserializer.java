package de.oth.regensburg.projektstudium.backend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PackageDeserializer extends StdDeserializer<Package> {

    private static final Logger log = LoggerFactory.getLogger(PackageDeserializer.class);

    public PackageDeserializer() {
        this(null);
    }

    public PackageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Package deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode pNode = jp.getCodec().readTree(jp);
        Package pkg = new Package();
        pkg.setId(pNode.get("id") != null ? pNode.get("id").asLong() : null);
        pkg.setBarcode(pNode.get("barcode") != null ? pNode.get("barcode").textValue() : null);
        pkg.setOrderNumber(pNode.get("orderNumber") != null ? pNode.get("orderNumber").textValue() : null);
        pkg.setType(pNode.get("type") != null ? PackageType.valueOf(pNode.get("type").asText()) : null);
        pkg.setStatus(pNode.get("status") != null ? PackageStatus.valueOf(pNode.get("status").asText()) : null);

        pkg.setRecipient(this.deserializeEmployee(pNode.get("recipient")));
        pkg.setSender(this.deserializeEmployee(pNode.get("sender")));
        return pkg;
    }

    private Employee deserializeEmployee(JsonNode pNode) {
        Employee employee = new Employee();
        if (pNode != null) {
            employee.setId(pNode.get("id") != null ? pNode.get("id").asLong() : null);
            employee.setName(pNode.get("name") != null ? pNode.get("name").textValue() : null);
            employee.setEmail(pNode.get("email") != null ? pNode.get("email").asText() : null);
            employee.setTelephone(pNode.get("telephone") != null ? pNode.get("telephone").asText() : null);
            employee.setFullAddress(pNode.get("fullAddress") != null ? pNode.get("fullAddress").asText() : null);

            JsonNode bNode = pNode.get("building");
            if (bNode != null) {
                Building building = new Building();
                building.setId(bNode.get("id") != null ? bNode.get("id").asLong() : null);
                employee.setBuilding(building);
            }
        }
        return employee;
    }
}
