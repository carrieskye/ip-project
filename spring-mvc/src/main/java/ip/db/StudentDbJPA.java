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
        manager = factory.createEntityManager();

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void addTestData() {
        add(new Student("r0458882", "Carolyne", "Peelman"));
        add(new Student("r0567231", "Jonas", "Goovaerts"));
    }

    @Override
    public Object get(long id) {
        try {
            return manager.find(Student.class, id);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            Query query = manager.createQuery("select s from Student s");
            return new ArrayList<Object>(query.getResultList());
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            Student student = (Student) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(student);
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
            Student student = (Student) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(student);
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
            Student student = (Student) get(id);
            manager.remove(student);
            manager.flush();
            t.commit();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    private void closeConnection() throws DbException {
        try {
            manager.close();
            factory.close();
        } catch (Exception e) {
            throw new DbException(e.getMessage(), e);
        }
    }
}
