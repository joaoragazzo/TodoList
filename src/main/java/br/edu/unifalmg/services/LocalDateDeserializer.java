package br.edu.unifalmg.services;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            if (jsonArray.size() == 3) {
                int year = jsonArray.get(0).getAsInt();
                int month = jsonArray.get(1).getAsInt();
                int day = jsonArray.get(2).getAsInt();

                return LocalDate.of(year, month, day);
            } else {
                throw new JsonParseException("Formato errado: a data é inválida");
            }
        } else {
            throw new JsonParseException("Formato inválido: era esperado um array de json");
        }
    }

}