package DataAccess;

import java.lang.reflect.GenericDeclaration;
import java.sql.SQLException;

public interface DAO {
    Object findByID(Object ID) throws SQLException;

    void insert(Object o);
    void update(Object o);
    void delete(Object o);
}
