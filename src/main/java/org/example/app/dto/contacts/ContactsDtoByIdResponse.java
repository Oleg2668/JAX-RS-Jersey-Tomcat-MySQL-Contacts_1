package org.example.app.dto.contacts;

import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contacts;

public record ContactsDtoByIdResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        Contacts contacts) {

    public static final String SUCCESS_MESSAGE = "Contacts with id %s has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Contacts with id %s has not been found!";

    public static org.example.app.dto.contacts.ContactsDtoByIdResponse of(Long id, boolean isContactsFound, Contacts contacts) {
        if (isContactsFound)
            return new org.example.app.dto.contacts.ContactsDtoByIdResponse(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE.formatted(id), contacts);
        else
            return new org.example.app.dto.contacts.ContactsDtoByIdResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    Response.Status.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE.formatted(id), null);
    }
}

