package com.example.testapp.serializer;

import com.example.testapp.entities.Cours;
import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Video;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class CoursSerializer extends JsonSerializer<Cours> {

    @Override
    public void serialize(Cours cours, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Sérialiser les champs nécessaires
        jsonGenerator.writeNumberField("idCour", cours.getIdCour());
        jsonGenerator.writeStringField("titreCours", cours.getTitreCours());
        jsonGenerator.writeStringField("descriptionCours", cours.getDescriptionCours());
        jsonGenerator.writeStringField("imagePath",cours.getImagePath());

        // Sérialisation des chapitres
        List<Chapitre> chapitres = cours.getChapitres();
        if (chapitres != null && !chapitres.isEmpty()) {
            jsonGenerator.writeArrayFieldStart("chapitres");
            for (Chapitre chapitre : chapitres) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("idchapitre", chapitre.getIdChapitre());
                jsonGenerator.writeStringField("titreChapitre", chapitre.getTitreChapitre());
                jsonGenerator.writeStringField("typeChapitre", chapitre.getTypeChapitre());
                jsonGenerator.writeStringField("contenuChapitre", chapitre.getContenuChapitre());


                // Sérialiser les vidéos associées au chapitre
                if (chapitre.getVideo() != null) {
                    jsonGenerator.writeObjectFieldStart("video");
                    Video video = chapitre.getVideo();
                    jsonGenerator.writeNumberField("id", video.getId());
                    jsonGenerator.writeStringField("titre", video.getTitre());
                    jsonGenerator.writeEndObject();
                }

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}
