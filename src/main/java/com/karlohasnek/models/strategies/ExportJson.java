package com.karlohasnek.models.strategies;

import com.google.gson.*;
import com.karlohasnek.models.PasswordEntry;
import org.hibernate.proxy.HibernateProxy;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportJson implements ExportStrategy {

    @Override
    public void export(String filePath, List<PasswordEntry> data) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return HibernateProxy.class.isAssignableFrom(clazz);
                    }
                })
                .create();

        String json = gson.toJson(data);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            writer.flush();
            System.out.println("JSON successfully exported to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
