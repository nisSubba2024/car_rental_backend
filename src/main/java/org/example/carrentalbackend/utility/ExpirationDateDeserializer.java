package org.example.carrentalbackend.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.YearMonth;

public class ExpirationDateDeserializer extends JsonDeserializer<YearMonth> {
    @Override
    public YearMonth deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String date = jsonParser.getText();
        return YearMonth.parse(date);
    }
}
