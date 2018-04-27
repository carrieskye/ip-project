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
        add(new Exam(1, LocalDate.of(2018, 6, 12), LocalTime.of(9, 0), LocalTime.of(12, 0), 2));
    }

    @Override
    public Object get(long id) {
        try {
            openConnection();
            Object object = manager.find(Exam.class,id);
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
            Query query = manager.createQuery("select e from Exam e");
            List<Object> exams = new ArrayList<Object>(query.getResultList());
            closeConnection();
            return exams;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        try {
            openConnection();
            Exam exam = (Exam) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.persist(exam);
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
            Exam exam = (Exam) object;
            EntityTransaction t = manager.getTransaction();
            t.begin();
            manager.merge(exam);
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
            Exam exam = manager.find(Exam.class,id);
            manager.remove(exam);
            manager.flush();
            t.commit();
            closeConnection();
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

}