package org.zk.simplemybatis.type;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanTypeHandler implements TypeHandler<Boolean> {

    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        boolean result = rs.getBoolean(columnName);
        return rs.wasNull() ? null : result;
    }
}
