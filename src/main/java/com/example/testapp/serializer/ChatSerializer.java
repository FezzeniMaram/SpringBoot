package com.example.testapp.serializer;

import com.example.testapp.entities.Chat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ChatSerializer extends JsonSerializer<Chat> {

    @Override
    public void serialize(Chat chat, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("idChat", chat.getIdChat());
        gen.writeStringField("messageChat", chat.getMessageChat());

        if (chat.getDateChat() != null) {
            String formattedDate = chat.getDateChat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            gen.writeStringField("dateChat", formattedDate);
        } else {
            gen.writeNullField("dateChat");
        }

        gen.writeStringField("expediteurRole", chat.getExpediteurRole());
        gen.writeBooleanField("visibleParEtudiant", chat.isVisibleParEtudiant());
        gen.writeBooleanField("visibleParTuteur", chat.isVisibleParTuteur());

        // ✅ Étudiant info
        if (chat.getEtudiant() != null) {
            gen.writeObjectFieldStart("etudiant");
            gen.writeNumberField("idEtudiant", chat.getEtudiant().getIdEtudiant());
            gen.writeStringField("nomEtudiant", chat.getEtudiant().getNomEtudiant());
            gen.writeEndObject();
        }


        // ✅ Tuteur info
        if (chat.getTuteur() != null) {
            gen.writeObjectFieldStart("tuteur");
            gen.writeNumberField("idTuteur", chat.getTuteur().getIdTuteur());
            gen.writeStringField("nomTuteur", chat.getTuteur().getNomTuteur());
            gen.writeEndObject();
        }

        gen.writeEndObject();
    }
}
