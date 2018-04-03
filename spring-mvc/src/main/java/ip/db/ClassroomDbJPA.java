package ip.db;

import ip.domain.Classroom;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    public ClassroomDbJPA() {
        factory = Persistence.createEntityManagerFactory("ip");
        manager = factory.createEntityManager();

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void addTestData() {
        add(new Classroom("D1.80", 200, "Aula"));
        add(new Classroom("D1.22", 40, "PC", 1));
        add(new Classroom("D1.27", 20, "Regular"));
    }

    @Override
    public Object get(long id) {
        try {
            return manager.find(Classroom.class, id);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            Query query = manager.createQuery("select c from Classroom c");
            return new ArrayList<Object>(query.getResultList());
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            Classroom classroom = (Classroom) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(classroom);
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
            Classroom classroom = (Classroom) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(classroom);
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
            Classroom classroom = (Classroom) get(id);
            manager.remove(classroom);
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
