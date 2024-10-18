package org.example.app.repository.contacts;

import org.example.app.dto.contacts.ContactsDtoRequest;
import org.example.app.entity.Contacts;
import org.example.app.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ContactsRepository extends BaseRepository<Contacts, ContactsDtoRequest> {
    // Створення нового запису
    void save(ContactsDtoRequest request);
    // Отримання всіх записів
    Optional<List<Contacts>> getAll();

    // ---- Path Params ----------------------
    // Отримання запису за id
    Optional<Contacts> getById(Long id);
    // Оновлення запису за id
    void update(Long id, ContactsDtoRequest request);
    // Видалення запису за id
    boolean deleteById(Long id);

    // ---- Utils ----------------------
    // Отримання останнього запису
    Optional<Contacts> getLastEntity();

    // ---- Query Params ----------------------
    Optional<List<Contacts>> fetchByFirstName(String firstName);
    Optional<List<Contacts>> fetchByLastName(String lastName);
    Optional<List<Contacts>> fetchAllOrderBy(String orderBy);
    Optional<List<Contacts>> fetchByLastNameOrderBy(String lastName, String orderBy);
    Optional<List<Contacts>> fetchBetweenIds(Integer from, Integer to);
    Optional<List<Contacts>> fetchLastNameIn(String lastName1, String lastName2);
}
