package DataAccess;

import java.lang.reflect.GenericDeclaration;

public interface DAO {
    Object findByID(Object ID);

    void insert(Object o);
    void update(Object o);
    void delete(Object o);
}
