package ua.kiev.prog.repository;

import ua.kiev.prog.tables.Group;

import java.util.List;

public interface GroupDAO {
    void add(Group group);
    void delete(Group group);
    Group findOne(long id);
    List<Group> list();
}
