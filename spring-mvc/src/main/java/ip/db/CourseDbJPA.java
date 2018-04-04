package ip.db;

import ip.domain.Course;
import ip.domain.Exam;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    CourseDbJPA() {
        factory = Persistence.createEntityManagerFactory("ip");
        manager = factory.createEntityManager();

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void addTestData() {
        add(new Course("MBI65X", "Webontwikkeling 3", 1, 1));
        add(new Course("MBI88X", "Testing", 2));
        add(new Course("MBI51X", "OO ontwerpen", 2));
        add(new Course("MBI39X", "Internetprogrammeren minor", 3));
    }

    @Override
    public Object get(long id) {
        try {
            return manager.find(Course.class, id);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            Query query = manager.createQuery("select c from Course c");
            return new ArrayList<Object>(query.getResultList());
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            Course course = (Course) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(course);
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
            Course course = (Course) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(course);
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
            Course course = (Course) get(id);
            manager.remove(course);
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