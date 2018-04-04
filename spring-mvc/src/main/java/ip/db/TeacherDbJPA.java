package ip.db;

import ip.domain.Student;
import ip.domain.Teacher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    TeacherDbJPA() {
        factory = Persistence.createEntityManagerFactory("ip");
        manager = factory.createEntityManager();

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void addTestData() {
        add(new Teacher("u0034728", "Elke", "Steegmans", 1));
        add(new Teacher("u0038275", "Mieke", "Kemme", 2));
        add(new Teacher("u0010298", "Kris", "Gabriëls", 1));
    }

    @Override
    public Object get(long id) {
        try {
            return manager.find(Teacher.class, id);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            Query query = manager.createQuery("select t from Teacher t");
            return new ArrayList<Object>(query.getResultList());
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            Teacher teacher = (Teacher) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(teacher);
            manager.flush();
            t.commit();
        } catch (Exception e) {
            EntityTransaction t = manager.getTransaction();
            t.rollback();
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Object object) {
        try {
            Teacher teacher = (Teacher) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(teacher);
            manager.flush();
            t.commit();
        } catch (Exception e) {
            EntityTransaction t = manager.getTransaction();
            t.rollback();
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            EntityTransaction t = manager.getTransaction();
            t.begin();
            Teacher teacher = (Teacher) get(id);
            manager.remove(teacher);
            manager.flush();
            t.commit();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public void closeConnection() throws DbException {
        try {
            manager.close();
            factory.close();
        } catch (Exception e) {
            throw new DbException(e.getMessage(), e);
        }
    }
}
