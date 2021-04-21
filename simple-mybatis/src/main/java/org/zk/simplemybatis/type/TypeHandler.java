package org.zk.simplemybatis.type;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {

    /**
     * 获取结果集
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    T getResult(ResultSet rs, String columnName) throws SQLException;
}
