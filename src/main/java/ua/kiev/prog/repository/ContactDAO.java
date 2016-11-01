package ua.kiev.prog.repository;

import ua.kiev.prog.tables.Contact;
import ua.kiev.prog.tables.Group;

import java.util.List;

public interface ContactDAO {
    void add(Contact contact);
    void delete(Contact contact);
    void delete(long[] ids);
    List<Contact> list(Group group);
    List<Contact> list(String pattern);
}
