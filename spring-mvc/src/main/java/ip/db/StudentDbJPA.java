package ip.db;

import ip.domain.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    StudentDbJPA() {
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
        add(new Student("r0458882", "Carolyne", "Peelman"));
        add(new Student("r0567231", "Jonas", "Goovaerts"));
    }

    @Override
    public Object get(long id) {
        try {
            openConnection();
            Object object = manager.find(Student.class,id);
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
            Query query = manager.createQuery("select s from Student s");
            List<Object> students = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return students;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            openConnection();
            Student student = (Student) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(student);
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
            Student student = (Student) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(student);
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
            Student student = manager.find(Student.class,id);
            manager.remove(student);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

}
