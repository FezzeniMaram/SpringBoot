package com.example.testapp.serializer;

import com.example.testapp.entities.Tuteur;
import com.example.testapp.entities.Cours;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TuteurSerializer extends JsonSerializer<Tuteur> {

    @Override
    public void serialize(Tuteur tuteur, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("nomTuteur", tuteur.getNomTuteur());
        jsonGenerator.writeStringField("emailTuteur", tuteur.getEmailTuteur());

        // Sérialiser uniquement les IDs des cours publiés
        List<Long> coursIds = tuteur.getCoursPublies() != null
                ? tuteur.getCoursPublies().stream().map(Cours::getIdCour).collect(Collectors.toList())
                : List.of();
        jsonGenerator.writeObjectField("coursPublies", coursIds);

        jsonGenerator.writeEndObject();
    }
}
