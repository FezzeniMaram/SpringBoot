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
        jsonGenerator.writeNumberField("id", etudiant.getIdEtudiant());
        jsonGenerator.writeStringField("nomEtudiant", etudiant.getNomEtudiant());
        jsonGenerator.writeStringField("emailEtudiant", etudiant.getEmailEtudiant());
        jsonGenerator.writeStringField("gender", etudiant.getGender() != null ? etudiant.getGender().toString() : null);
        jsonGenerator.writeStringField("dateNaissance", etudiant.getDateNaissanceEtudiant() != null ? etudiant.getDateNaissanceEtudiant().toString() : null);


        jsonGenerator.writeStringField("role", etudiant.getRole().toString());

        List<Long> coursIds = etudiant.getCoursInscrits() != null
                ? etudiant.getCoursInscrits().stream().map(Cours::getIdCour).collect(Collectors.toList())
                : List.of();
        jsonGenerator.writeObjectField("coursInscrits", coursIds);

        jsonGenerator.writeEndObject();
    }
}
