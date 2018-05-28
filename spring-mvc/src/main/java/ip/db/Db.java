package ip.db;

import java.util.List;

public interface Db {

    Object get(long id);

    List<Object> getAll();

    List<Object> getAllSorted(String column);

    void add(Object object);

    void update(Object object);

    void delete(long id);
}
