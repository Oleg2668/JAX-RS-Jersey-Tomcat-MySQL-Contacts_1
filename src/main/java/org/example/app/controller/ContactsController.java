package org.example.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.dto.contacts.*;
import org.example.app.entity.Contacts;
import org.example.app.service.contacts.ContactsService;

import java.util.Collections;
import java.util.List;

// Вхідна точка (REST-контроллер)
@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsController {

    @Inject
    private ContactsService contactsService;

    // Створення нового запису
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContacts(final ContactsDtoRequest request) {
        Contacts contacts = contactsService.create(request);
        if (contacts != null)
            return Response.ok()
                    .entity(ContactsDtoCreateResponse.of(true, contacts))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoCreateResponse.of(false, null))
                    .build();
    }

    // Отримання всіх записів
    @GET
    public Response getAllContacts() {
        List<Contacts> list = contactsService.getAll();
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }


    // ---- Path Params ----------------------

    // Отримання запису за id
    @GET
    @Path("{id: [1-9][0-9]*}")
    public Response getContactsById(@PathParam("id") final Long id) {
        Contacts contacts = contactsService.getById(id);
        if (contacts != null)
            return Response.ok()
                    .entity(ContactsDtoByIdResponse.of(id, true, contacts))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoByIdResponse.of(id, false, null))
                    .build();
    }

    // Оновлення запису за id
    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContactsById(@PathParam("id") final Long id, final ContactsDtoRequest request) {
        Contacts contactsToUpdate = contactsService.getById(id);
        if (contactsToUpdate != null) {
            Contacts contactsUpdated = contactsService.update(id, request);
            return Response.ok()
                    .entity(ContactsDtoUpdateResponse.of(id, true, contactsUpdated))
                    .build();
        } else {
            return Response.ok()
                    .entity(ContactsDtoUpdateResponse.of(id, false, null))
                    .build();
        }
    }

    // Видалення запису за id
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteUserById(@PathParam("id") final Long id) {
        if (contactsService.deleteById(id))
            return Response.ok()
                    .entity(ContactsDtoDeleteResponse.of(id, true))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoDeleteResponse.of(id, false))
                    .build();
    }


    // ---- Query Params ----------------------

    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-firstname?firstName=Tom
        If firstName does not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-firstname?firstName=Tomas
    */
    @GET
    @Path("/query-by-firstname")
    public Response fetchByFirstName(@QueryParam("firstName") final String firstName) {
        List<Contacts> list = contactsService.fetchByFirstName(firstName);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }

    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname?lastName=Bright
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname?lastName=Terra
        If lastName does not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname?lastName=Mars
    */
    @GET
    @Path("/query-by-lastname")
    public Response fetchByLastName(@QueryParam("lastName") final String lastName) {
        List<Contacts> list = contactsService.fetchByLastName(lastName);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }

    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-order-by?orderBy=firstName
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-order-by?orderBy=lastName
    */
    @GET
    @Path("/query-order-by")
    public Response fetchAllOrderBy(@QueryParam("orderBy") final String orderBy) {
        List<Contacts> list = contactsService.fetchAllOrderBy(orderBy);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }


    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname-order-by?lastName=Bright&orderBy=firstName
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname-order-by?lastName=Bright&orderBy=email
        If lastName does not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-by-lastname-order-by?lastName=Mars&orderBy=firstName
    */
    @GET
    @Path("/query-by-lastname-order-by")
    public Response fetchByLastNameOrderBy(
            @QueryParam("lastName") final String lastName,
            @QueryParam("orderBy") final String orderBy
    ) {
        List<Contacts> list =
                contactsService.fetchByLastNameOrderBy(lastName, orderBy);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }

    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-between-ids?from=3&to=6
        If ids do not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-between-ids?from=9&to=12
    */
    @GET
    @Path("/query-between-ids")
    public Response fetchBetweenIds(
            @QueryParam("from") final int from,
            @QueryParam("to") final int to
    ) {
        List<Contacts> list = contactsService.fetchBetweenIds(from, to);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }

    /*
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-lastname-in?lastName1=Terra&lastName2=Bright
        If lastName1 does not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-lastname-in?lastName1=Mars&lastName2=Bright
        If lastName2 does not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-lastname-in?lastName1=Terra&lastName2=Forest
        If lastName1 and lastName2 do not exist
        http://localhost:8080/Your-Project-Name-1.0-SNAPSHOT/api/v1/users/query-lastname-in?lastName1=Mars&lastName2=Forest
    */
    @GET
    @Path("/query-lastname-in")
    public Response fetchLastNameIn(
            @QueryParam("lastName1") final String lastName1,
            @QueryParam("lastName2") final String lastName2
    ) {
        List<Contacts> list =
                contactsService.fetchLastNameIn(lastName1, lastName2);
        if (list.isEmpty())
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(true, Collections.emptyList()))
                    .build();
        else
            return Response.ok()
                    .entity(ContactsDtoListResponse.of(false, list))
                    .build();
    }
}