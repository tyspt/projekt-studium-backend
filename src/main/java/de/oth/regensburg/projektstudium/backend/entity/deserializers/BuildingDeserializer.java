package de.oth.regensburg.projektstudium.backend.entity.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BuildingDeserializer extends StdDeserializer<Building> {

    private static final Logger log = LoggerFactory.getLogger(BuildingDeserializer.class);

    public BuildingDeserializer() {
        this(null);
    }

    public BuildingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Building deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode pNode = jp.getCodec().readTree(jp);
        return new Building(pNode);
    }
}
