package org.example.app.repository.contacts;
import org.example.app.config.HibernateUtil;
import org.example.app.dto.contacts.ContactsDtoRequest;
import org.example.app.entity.Contacts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// Репозиторій, який безпосередньо
// маніпулює даними в БД.
public class ContactsRepositoryImpl implements ContactsRepository {

    @Override
    public void save(ContactsDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            String hql = "INSERT INTO Contacts (firstName, lastName, phone) " +
                    "VALUES (:firstName, :lastName, :phone)";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", request.firstName());
            query.setParameter("lastName", request.lastName());
            query.setParameter("phone", request.phone());
            query.executeUpdate();
            // Транзакція виконується
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<List<Contacts>> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Contacts> list =
                    session.createQuery("FROM Contacts", Contacts.class).list();
            // Транзакція виконується
            transaction.commit();
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (Exception e) {
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    // ---- Path Params ----------------------

    @Override
    public Optional<Contacts> getById(Long id) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query<Contacts> query =
                    session.createQuery("FROM Contacts WHERE id = :id", Contacts.class);
            query.setParameter("id", id);
            query.setMaxResults(1);
          Contacts contacts= query.uniqueResult();
            // Транзакція виконується
            transaction.commit();
            // Повертаємо Optional-контейнер з об'єктом
            return Optional.ofNullable(contacts);
        } catch (Exception e) {
            // Якщо помилка або такого об'єкту немає в БД,
            // повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, ContactsDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакция стартует
            transaction = session.beginTransaction();
            String hql = "UPDATE Contacts SET firstName = :firstName," +
                    " lastName = :lastName, phone = :phone" +
                    " WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", request.firstName());
            query.setParameter("lastName", request.lastName());
            query.setParameter("phone", request.phone());
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Contacts WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            // Транзакція виконується
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    // ---- Utils ----------------------

    @Override
    public Optional<Contacts> getLastEntity() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query<Contacts> query =
                    session.createQuery("FROM Contacts ORDER BY id DESC", Contacts.class);
            query.setMaxResults(1);
            Contacts contacts = query.uniqueResult();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(contacts);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }

    // ---- Query Params ----------------------

    public Optional<List<Contacts>> fetchByFirstName(String firstName) {
        String hql = "FROM Contacts WHERE firstName = :firstName";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query<Contacts> query = session.createQuery(hql, Contacts.class);
            query.setParameter("firstName", firstName);
            List<Contacts> list = query.list();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Contacts>> fetchByLastName(String lastName) {
        String hql = "FROM Contacts WHERE lastName = :lastName";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query< Contacts> query = session.createQuery(hql, Contacts.class);
            query.setParameter("lastName", lastName);
            List< Contacts> list = query.list();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Contacts>> fetchAllOrderBy(String orderBy) {
        String hql = "FROM Contacts ORDER BY " + orderBy;
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Contacts> list =
                    session.createQuery(hql, Contacts.class)
                            .list();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Contacts>> fetchByLastNameOrderBy(String lastName, String orderBy) {
        String hql = "FROM Contacts WHERE lastName = :lastName ORDER BY " + orderBy;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Contacts> list = session.createQuery(hql, Contacts.class)
                    .setParameter("lastName", lastName)
                    .list();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Contacts>> fetchBetweenIds(Integer from, Integer to) {
        String hql = "FROM Contacts u WHERE u.id BETWEEN :from AND :to";
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Contacts> list =
                    session.createQuery(hql, Contacts.class)
                            .setParameter("from", from)
                            .setParameter("to", to)
                            .list();

            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Contacts>> fetchLastNameIn(String lastName1, String lastName2) {
        String hql = "FROM Contacts u WHERE u.lastName IN (:lastName1, :lastName2)";
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Contacts> list =
                    session.createQuery(hql, Contacts.class)
                            .setParameter("lastName1", lastName1)
                            .setParameter("lastName2", lastName2)
                            .list();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}