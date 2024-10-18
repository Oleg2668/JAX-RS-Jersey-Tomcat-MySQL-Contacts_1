package org.example.app.config;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.example.app.repository.contacts.ContactsRepository;
import org.example.app.repository.contacts.ContactsRepositoryImpl;
import org.example.app.service.contacts.ContactsService;
import org.example.app.service.contacts.ContactsServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;


public class ApplicationBinder implements Feature {

    /**
     * Метод реєструє нижчеприведені класи в контексті контейнера, який буде
     * за автоматично керувати залежностями (dependency injection).
     */
    @Override
    public boolean configure(FeatureContext context) {
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ContactsRepositoryImpl.class).to(ContactsRepository.class);
                bind(ContactsServiceImpl.class).to(ContactsService.class);
                // Цей клас потрібен для роботи із JSON форматом даних
                bind(JacksonJsonProvider.class);
            }
        });
        return true;
    }
}
