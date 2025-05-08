package com.example.testapp.serializer;

import com.example.testapp.entities.Conversation;
import com.example.testapp.entities.Chat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ConversationSerializer extends JsonSerializer<Conversation> {

    @Override
    public void serialize(Conversation conv, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", conv.getId());

        // ✅ Etudiant
        if (conv.getEtudiant() != null) {
            gen.writeObjectFieldStart("etudiant");
            gen.writeNumberField("idEtudiant", conv.getEtudiant().getIdEtudiant());
            gen.writeStringField("nomEtudiant", conv.getEtudiant().getNomEtudiant());
            if (conv.getEtudiant().getGender() != null) {
                gen.writeStringField("genderEtudiant", conv.getEtudiant().getGender().toString());
            } else {
                gen.writeStringField("genderEtudiant", "HOMME"); // valeur par défaut
            }
            gen.writeEndObject();
        }

        // ✅ Tuteur
        if (conv.getTuteur() != null) {
            gen.writeObjectFieldStart("tuteur");
            gen.writeNumberField("idTuteur", conv.getTuteur().getIdTuteur());
            gen.writeStringField("nomTuteur", conv.getTuteur().getNomTuteur());
            if (conv.getTuteur().getGender() != null) {
                gen.writeStringField("genderTuteur", conv.getTuteur().getGender().toString());
            } else {
                gen.writeStringField("genderTuteur", "HOMME"); // valeur par défaut
            }
            gen.writeEndObject();
        }

        gen.writeBooleanField("visibleParEtudiant", conv.isVisibleParEtudiant());
        gen.writeBooleanField("visibleParTuteur", conv.isVisibleParTuteur());
        gen.writeBooleanField("etudiantBloqueTuteur", conv.isEtudiantBloqueTuteur());
        gen.writeBooleanField("tuteurBloqueEtudiant", conv.isTuteurBloqueEtudiant());

        // ✅ Dernier message
        gen.writeStringField("lastMessage", conv.getLastMessage() != null ? conv.getLastMessage() : "");

        gen.writeEndObject();
    }
}
