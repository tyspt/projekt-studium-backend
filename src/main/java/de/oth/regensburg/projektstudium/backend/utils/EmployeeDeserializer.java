package de.oth.regensburg.projektstudium.backend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EmployeeDeserializer extends StdDeserializer<Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeDeserializer.class);

    public EmployeeDeserializer() {
        this(null);
    }

    public EmployeeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Employee deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode pNode = jp.getCodec().readTree(jp);
        return new Employee(pNode);
    }
}
