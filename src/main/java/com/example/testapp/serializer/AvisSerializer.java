package com.example.testapp.serializer;

import com.example.testapp.entities.Avis;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AvisSerializer extends JsonSerializer<Avis> {

    @Override
    public void serialize(Avis avis, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("idAvis", avis.getIdAvis());
        jsonGenerator.writeStringField("commentaireAvis", avis.getCommentaireAvis());

        // ✅ Auteur étudiant
        if (avis.getEtudiant() != null) {
            jsonGenerator.writeStringField("auteur", avis.getEtudiant().getNomEtudiant());
            jsonGenerator.writeStringField("typeAuteur", "ETUDIANT");
            jsonGenerator.writeStringField("emailAuteur", avis.getEtudiant().getEmailEtudiant());
        }
        // ✅ Auteur tuteur
        else if (avis.getTuteur() != null) {
            jsonGenerator.writeStringField("auteur", avis.getTuteur().getNomTuteur());
            jsonGenerator.writeStringField("typeAuteur", "TUTEUR");
            jsonGenerator.writeStringField("emailAuteur", avis.getTuteur().getEmailTuteur());
        } else {
            jsonGenerator.writeStringField("auteur", "Inconnu");
            jsonGenerator.writeStringField("typeAuteur", "Inconnu");
            jsonGenerator.writeStringField("emailAuteur", "N/A");
        }

        // ✅ Informations du cours
        if (avis.getCours() != null) {
            jsonGenerator.writeObjectFieldStart("cours");
            jsonGenerator.writeNumberField("idCour", avis.getCours().getIdCour());
            jsonGenerator.writeStringField("titreCours", avis.getCours().getTitreCours());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndObject();
    }


}
