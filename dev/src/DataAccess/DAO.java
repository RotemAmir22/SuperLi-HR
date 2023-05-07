package DataAccess;

import java.lang.reflect.GenericDeclaration;
import java.sql.SQLException;

public interface DAO {
    Object findByID(Object ID) throws SQLException, ClassNotFoundException;

    void insert(Object o) throws SQLException;
    void update(Object o);
    void delete(Object o);
}
