package org.zk.simplemybatis.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DateTypeHandler implements TypeHandler<java.util.Date> {

    public java.util.Date getResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp != null) {
            return new java.util.Date(timestamp.getTime());
        } else {
            return null;
        }
    }
}
