package ip.db;

import ip.domain.Exam;
import ip.domain.Teacher;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExamDbJPA implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;

    ExamDbJPA() {
        factory = Persistence.createEntityManagerFactory("ip");
        manager = factory.createEntityManager();

        if (getAll().size() == 0) {
            addTestData();
        }
    }

    private void addTestData() {
        add(new Exam(1, LocalDate.of(2018, 6, 12), LocalTime.of(9, 0), LocalTime.of(12, 0), 2));
    }

    @Override
    public Object get(long id) {
        try {
            return manager.find(Exam.class, id);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {
        try {
            Query query = manager.createQuery("select e from Exam e");
            return new ArrayList<Object>(query.getResultList());
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            Exam exam = (Exam) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(exam);
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
            Exam exam = (Exam) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(exam);
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
            Exam exam = (Exam) get(id);
            manager.remove(exam);
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