package de.oth.regensburg.projektstudium.backend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.*;

import java.io.IOException;

public class PackageDeserializer extends StdDeserializer<Package> {

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

        pkg.setRecipient(this.deserializePerson(pNode.get("recipient")));
        pkg.setSender(this.deserializePerson(pNode.get("sender")));
        return pkg;
    }

    private Person deserializePerson(JsonNode pNode) {
        Person person = new Person();
        if (pNode != null) {
            person.setId(pNode.get("id") != null ? pNode.get("id").asLong() : null);
            person.setName(pNode.get("name") != null ? pNode.get("name").textValue() : null);
            person.setEmail(pNode.get("email") != null ? pNode.get("email").asText() : null);
            person.setTelephone(pNode.get("telephone") != null ? pNode.get("telephone").asText() : null);
            person.setFullAddress(pNode.get("fullAddress") != null ? pNode.get("fullAddress").asText() : null);

            JsonNode bNode = pNode.get("building");
            if (bNode != null) {
                Building building = new Building();
                building.setId(bNode.get("id") != null ? bNode.get("id").asLong() : null);
                person.setBuilding(building);
            }
//        person.setRepresentative(pNode.get("representative") != null ? pNode.get("representative").textValue(): null);
        }
        return person;
    }
}
