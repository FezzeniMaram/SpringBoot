package com.example.testapp.serializer;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Video;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ChapitreSerializer extends JsonSerializer<Chapitre> {

    @Override
    public void serialize(Chapitre chapitre, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Sérialiser les champs du chapitre
        jsonGenerator.writeNumberField("idchapitre", chapitre.getIdChapitre());
        jsonGenerator.writeStringField("titreChapitre", chapitre.getTitreChapitre());
        jsonGenerator.writeStringField("contenuChapitre", chapitre.getContenuChapitre());
        jsonGenerator.writeStringField("videoPath", chapitre.getVideoPath());

        if (chapitre.getCours() != null) {
            jsonGenerator.writeObjectFieldStart("cours");
            jsonGenerator.writeNumberField("idCour", chapitre.getCours().getIdCour());
            jsonGenerator.writeEndObject();
        }


        jsonGenerator.writeEndObject();
    }
}
