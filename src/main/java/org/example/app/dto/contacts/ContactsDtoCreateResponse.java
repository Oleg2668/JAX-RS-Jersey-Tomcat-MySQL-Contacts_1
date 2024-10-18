package org.example.app.dto.contacts;

import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contacts;
public record ContactsDtoCreateResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
       Contacts contacts) {

    public static final String SUCCESS_MESSAGE = "Contacts has been created successfully.";
    public static final String FAILURE_MESSAGE = "Contacts has not been created!";

    public static org.example.app.dto.contacts.ContactsDtoCreateResponse of(boolean isContactsCreated, Contacts contacts) {
        if (isContactsCreated)
            return new org.example.app.dto.contacts.ContactsDtoCreateResponse(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE, contacts);
        else
            return new org.example.app.dto.contacts.ContactsDtoCreateResponse(
                    Response.Status.NO_CONTENT.getStatusCode(),
                    Response.Status.NO_CONTENT.getReasonPhrase(),
                    false, FAILURE_MESSAGE, null);
    }
}
