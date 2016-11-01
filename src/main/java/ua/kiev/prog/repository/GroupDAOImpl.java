package ua.kiev.prog.repository;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.tables.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Group group) {
        entityManager.persist(group);
    }

    @Override
    public void delete(Group group) {
        entityManager.remove(group);
    }

    @Override
    public Group findOne(long id) {
        return entityManager.getReference(Group.class, id);
    }

    @Override
    public List<Group> list() {
        Query query = entityManager.createQuery("SELECT g FROM Group g", Group.class);
        return (List<Group>) query.getResultList();
    }
}
