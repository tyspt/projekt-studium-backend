package de.oth.regensburg.projektstudium.backend.entity.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.Package;
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
        return new Package(pNode);
    }
}
