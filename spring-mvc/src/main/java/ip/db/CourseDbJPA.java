package ip.db;

import ip.domain.Course;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    CourseDbJPA() {
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
        add(new Course("MBI65X", "Webontwikkeling 3", 1, 1));
        add(new Course("MBI88X", "Testing", 2,1));
        add(new Course("MBI51X", "OO ontwerpen", 2,1));
        add(new Course("MBI39X", "Internetprogrammeren minor", 3));
    }

    @Override
    public Object get(long id) {
        try {
            openConnection();
            Object object = manager.find(Course.class, id);
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
            Query query = manager.createQuery("select c from Course c");
            List<Object> courses = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return courses;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAllSorted(String column) {
        try {
            openConnection();
            Query query = manager.createQuery("select c from Course c order by c." + column);
            List<Object> courses = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return courses;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            openConnection();
            Course course = (Course) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(course);
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
            Course course = (Course) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(course);
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
            Course course = manager.find(Course.class, id);
            manager.remove(course);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

}