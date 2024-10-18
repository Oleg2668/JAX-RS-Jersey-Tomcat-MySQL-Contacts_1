package org.example.app.service.contacts;

import jakarta.inject.Inject;
import org.example.app.dto.contacts.ContactsDtoRequest;
import org.example.app.entity.Contacts;
import org.example.app.repository.contacts.ContactsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContactsServiceImpl implements ContactsService {

    // Анотація @Inject для вбудування (ін'єкції)
    // об'єкту репозиторія в цей клас
    @Inject
    private ContactsRepository repository;

    @Override
    public Contacts create(ContactsDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        repository.save(request);
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<Contacts> getAll() {
        return repository.getAll()
                .orElse(Collections.emptyList());
    }

    // ---- Path Params ----------------------

    @Override
    public Contacts getById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        return repository.getById(id).orElse(null);
    }

    @Override
    public Contacts update(Long id,ContactsDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided!");
        }
        if (repository.getById(id).isPresent()) {
            repository.update(id, request);
        }
        return repository.getById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        if (repository.getById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // ---- Query Params ----------------------

    public List<Contacts> fetchByFirstName(String firstName) {
        return repository.fetchByFirstName(firstName)
                .orElse(Collections.emptyList());
    }

    public List<Contacts> fetchByLastName(String lastName) {
        return repository.fetchByLastName(lastName)
                .orElse(Collections.emptyList());
    }

    public List<Contacts> fetchAllOrderBy(String orderBy) {
        return repository.fetchAllOrderBy(orderBy)
                .orElse(Collections.emptyList());
    }

    public List<Contacts> fetchByLastNameOrderBy(String lastName,
                                             String orderBy) {
        return repository.fetchByLastNameOrderBy(lastName,
                orderBy).orElse(Collections.emptyList());
    }

    public List<Contacts> fetchBetweenIds(int from, int to) {
        return repository.fetchBetweenIds(from, to)
                .orElse(Collections.emptyList());
    }

    public List<Contacts> fetchLastNameIn(String lastName1,
                                      String lastName2) {
        return repository.fetchLastNameIn(lastName1,
                        lastName2)
                .orElse(Collections.emptyList());
    }
}