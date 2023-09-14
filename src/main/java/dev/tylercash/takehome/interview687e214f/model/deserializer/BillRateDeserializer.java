package dev.tylercash.takehome.interview687e214f.model.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BillRateDeserializer extends StdDeserializer<BigDecimal>{
    public BillRateDeserializer() {
        this(null);
    }

    public BillRateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();

        String value = ((JsonNode) codec.readTree(p)).asText();
        return new BigDecimal(value.replace("$", ""));
    }
}
