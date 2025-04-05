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
        jsonGenerator.writeStringField("typeChapitre", chapitre.getTypeChapitre());
        jsonGenerator.writeStringField("contenuChapitre", chapitre.getContenuChapitre());


        // Vérifier si une vidéo existe et la sérialiser
        if (chapitre.getVideo() != null) {
            jsonGenerator.writeObjectFieldStart("video");
            Video video = chapitre.getVideo();
            jsonGenerator.writeNumberField("id", video.getId());
            jsonGenerator.writeStringField("titre", video.getTitre());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndObject();
    }
}
