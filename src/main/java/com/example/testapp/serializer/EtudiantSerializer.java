package com.example.testapp.serializer;

import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Cours;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EtudiantSerializer extends JsonSerializer<Etudiant> {
    @Override
    public void serialize(Etudiant etudiant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("nomEtudiant", etudiant.getNomEtudiant());
        jsonGenerator.writeStringField("emailEtudiant", etudiant.getEmailEtudiant());

        List<Long> coursIds = etudiant.getCoursInscrits() != null
                ? etudiant.getCoursInscrits().stream().map(Cours::getIdCour).collect(Collectors.toList())
                : List.of();
        jsonGenerator.writeObjectField("coursInscrits", coursIds);

        jsonGenerator.writeEndObject();
    }
}
