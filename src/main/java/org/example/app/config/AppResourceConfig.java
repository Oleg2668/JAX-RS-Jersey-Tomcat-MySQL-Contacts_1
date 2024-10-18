package org.example.app.config;

import jakarta.ws.rs.ApplicationPath;
import org.example.app.controller.ContactsController;
import org.glassfish.jersey.server.ResourceConfig;

// @ApplicationPath описує кореневий шлях,
// який буде використовуватися у всіх наявних
// веб-сервісах (REST-контроллерах)
@ApplicationPath("/api/v1")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        // Реєстрація контролера(ів) для подальшої
        // його (іх) ініціалізації (створення)
        register(ContactsController.class);
        // Реєстрація конфігураційного класу
        // і наявних в ньому класів
        register(ApplicationBinder.class);
    }
}
