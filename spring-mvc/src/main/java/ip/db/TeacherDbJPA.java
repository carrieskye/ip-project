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

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void openConnection() {
        manager = factory.createEntityManager();
    }

    private void closeConnection() throws DbException {
        try {
            manager.close();
        } catch (Exception e) {
            throw new DbException(e.getMessage(), e);
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
            openConnection();
            Object object = manager.find(Teacher.class, id);
            closeConnection();
            return object;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            openConnection();
            Query query = manager.createQuery("select t from Teacher t");
            List<Object> teachers = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return teachers;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAllSorted(String column) {
        try {
            openConnection();
            Query query = manager.createQuery("select t from Teacher t order by t." + column);
            List<Object> teachers = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return teachers;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            openConnection();
            Teacher teacher = (Teacher) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(teacher);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            EntityTransaction t = manager.getTransaction();
            t.rollback();
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Object object) {
        try {
            openConnection();
            Teacher teacher = (Teacher) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(teacher);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            EntityTransaction t = manager.getTransaction();
            t.rollback();
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            openConnection();
            EntityTransaction t = manager.getTransaction();
            t.begin();
            Teacher teacher = manager.find(Teacher.class, id);
            manager.remove(teacher);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

}
