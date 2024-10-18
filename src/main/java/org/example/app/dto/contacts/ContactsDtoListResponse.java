package org.example.app.dto.contacts;

import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contacts;

import java.util.Collections;
import java.util.List;

public record ContactsDtoListResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        List<Contacts> userList) {

    public static final String SUCCESS_MESSAGE = "Contacts list has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Contacts list has not been found!";

    public static org.example.app.dto.contacts.ContactsDtoListResponse of(boolean isContactsListEmpty, List<Contacts> contactsList) {
        if (isContactsListEmpty)
            return new org.example.app.dto.contacts.ContactsDtoListResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    Response.Status.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE, Collections.emptyList());
        else
            return new org.example.app.dto.contacts.ContactsDtoListResponse(
                    Response.Status.OK.getStatusCode(),
                    Response.Status.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE, contactsList);
    }
}
