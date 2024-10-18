package org.example.app.dto.contacts;

import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contacts;

public record ContactsDtoUpdateResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        Contacts contacts) {

    public static final String SUCCESS_MESSAGE = "Contacts with id %s has been updated successfully.";
    public static final String FAILURE_MESSAGE = "Contacts with id %s has not been found!";

    public static org.example.app.dto.contacts.ContactsDtoUpdateResponse of(Long id, boolean isContactsFound, Contacts contactsUpdated) {
        if (isContactsFound)
            return new org.example.app.dto.contacts.ContactsDtoUpdateResponse(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE.formatted(id), contactsUpdated);
        else
            return new org.example.app.dto.contacts.ContactsDtoUpdateResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    Response.Status.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE.formatted(id), null);
    }
}
