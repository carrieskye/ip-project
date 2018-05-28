package ip.db;

import ip.domain.Classroom;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    ClassroomDbJPA() {
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
        add(new Classroom("D0.80", 200, "Aula", 1));
        add(new Classroom("D1.80", 200, "Aula"));
        add(new Classroom("D1.22", 40, "PC",2));
        add(new Classroom("D1.27", 20, "Regular"));
    }

    @Override
    public Object get(long id) {
        try {
            openConnection();
            Object object = manager.find(Classroom.class, id);
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
            Query query = manager.createQuery("select c from Classroom c");
            List<Object> classrooms = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return classrooms;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAllSorted(String column) {
        try {
            openConnection();
            Query query = manager.createQuery("select c from Classroom c order by c." + column);
            List<Object> classrooms = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return classrooms;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            openConnection();
            Classroom classroom = (Classroom) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(classroom);
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
            Classroom classroom = (Classroom) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(classroom);
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
            Classroom classroom = manager.find(Classroom.class, id);
            manager.remove(classroom);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

}
