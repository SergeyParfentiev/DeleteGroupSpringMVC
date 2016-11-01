package ua.kiev.prog.repository;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.tables.Contact;
import ua.kiev.prog.tables.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Contact contact) {
        entityManager.merge(contact);
    }

    @Override
    public void delete(Contact contact) {
        entityManager.remove(contact);
    }

    @Override
    public void delete(long[] ids) {
        Contact c;
        for (long id : ids) {
            c = entityManager.getReference(Contact.class, id);
            entityManager.remove(c);
        }
    }

    @Override
    public List<Contact> list(Group group) {
        Query query;

        if (group != null) {
            query = entityManager.createQuery("SELECT c FROM Contact c WHERE c.group = :group", Contact.class);
            query.setParameter("group", group);
        } else {
            query = entityManager.createQuery("SELECT c FROM Contact c", Contact.class);
        }

        return (List<Contact>) query.getResultList();
    }

    @Override
    public List<Contact> list(String pattern) {
        Query query = entityManager.createQuery("SELECT c FROM Contact c WHERE c.name LIKE :pattern", Contact.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Contact>) query.getResultList();
    }
}