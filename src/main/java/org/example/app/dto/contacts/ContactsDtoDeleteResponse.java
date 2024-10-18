package org.example.app.dto.contacts;

import jakarta.ws.rs.core.Response;

public record ContactsDtoDeleteResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message) {

    public static final String SUCCESS_MESSAGE = "Contacts with id %s has been deleted successfully.";
    public static final String FAILURE_MESSAGE = "Contacts with id %s has not been found!";

    public static org.example.app.dto.contacts.ContactsDtoDeleteResponse of(Long id, boolean isContactsFound) {
        if (isContactsFound)
            return new org.example.app.dto.contacts.ContactsDtoDeleteResponse(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE.formatted(id));
        else
            return new org.example.app.dto.contacts.ContactsDtoDeleteResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    Response.Status.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE.formatted(id));
    }
}
