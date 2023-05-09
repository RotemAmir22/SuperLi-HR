package DataAccess;

import java.sql.SQLException;

public interface IDAO_DailyShift {

    Object findByKey(Object date, Object id) throws SQLException, ClassNotFoundException;

    void insert(Object o) throws SQLException;
    void update(Object o) throws SQLException;
    void delete(Object o) throws SQLException;
}
