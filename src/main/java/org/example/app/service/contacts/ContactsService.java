package org.example.app.service.contacts;

import org.example.app.dto.contacts.ContactsDtoRequest;
import org.example.app.entity.Contacts;
import org.example.app.service.BaseService;

import java.util.List;

public interface ContactsService extends BaseService<Contacts, ContactsDtoRequest> {
    // Створення нового запису
    Contacts create(ContactsDtoRequest request);
    // Отримання всіх записів
    List<Contacts> getAll();

    // ---- Path Params ----------------------
    // Отримання запису за id
    Contacts getById(Long id);
    // Оновлення запису за id
    Contacts update(Long id, ContactsDtoRequest request);
    // Видалення запису за id
    boolean deleteById(Long id);

    // ---- Query Params ----------------------
    List<Contacts> fetchByFirstName(String firstName);
    List<Contacts> fetchByLastName(String lastName);
    List<Contacts> fetchAllOrderBy(String orderBy);
    List<Contacts> fetchByLastNameOrderBy(String lastName, String orderBy);
    List<Contacts> fetchBetweenIds(int from, int to);
    List<Contacts> fetchLastNameIn(String lastName1, String lastName2);
}
