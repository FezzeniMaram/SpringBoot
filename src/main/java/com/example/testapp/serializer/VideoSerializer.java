package com.example.testapp.serializer;

import com.example.testapp.entities.Video;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class VideoSerializer extends JsonSerializer<Video> {

    @Override
    public void serialize(Video video, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Sérialiser les propriétés de la vidéo
        jsonGenerator.writeNumberField("id", video.getId());
        jsonGenerator.writeStringField("titre", video.getTitre());

        jsonGenerator.writeEndObject();
    }
}
