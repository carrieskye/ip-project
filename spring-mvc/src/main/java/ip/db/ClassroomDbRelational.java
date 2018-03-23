package ip.db;

import ip.domain.Classroom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ClassroomDbRelational implements Db {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private String name;

//    public void openConnection(String name){
//        factory = Persistence.createEntityManagerFactory(name);
//        manager = factory.createEntityManager();
//    }
//
//    public void closeConnection() throws DbException {
//        try {
//            manager.close();
//            factory.close();
//        } catch (Exception e) {
//            throw new DbException(e.getMessage(), e);
//        }
//    }

    public Object get(long id) {
        //TODO
        return null;
    }

    public List<Object> getAll() {
        //TODO
        return null;
    }

    public void add(Object object) {
        //TODO
    }

    public void update(Object object) {
        //TODO
    }

    public void delete(long id) {
        //TODO
    }
}
