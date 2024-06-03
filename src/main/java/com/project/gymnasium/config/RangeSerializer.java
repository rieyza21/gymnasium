package com.project.gymnasium.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.hypersistence.utils.hibernate.type.range.Range;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RangeSerializer extends JsonSerializer<Range<LocalDateTime>> {

    @Override
    public void serialize(Range<LocalDateTime> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        LocalDateTime start = value.lower();
        LocalDateTime end = value.upper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String rangeString = "'" + start.format(formatter) + "','" + end.format(formatter) + "'";
        gen.writeString(rangeString);
    }
}