package com.project.gymnasium.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.hypersistence.utils.hibernate.type.range.Range;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RangeDeserializer extends JsonDeserializer<Range<LocalDateTime>> {
    @Override
    public Range<LocalDateTime> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String rangeString = null;
        try {
            rangeString = jsonParser.getText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] parts = rangeString.split(",");
        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Range.closed(start, end);
    }
}
