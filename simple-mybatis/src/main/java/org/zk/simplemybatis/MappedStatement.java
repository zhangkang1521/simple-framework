package org.zk.simplemybatis;

public class MappedStatement {
    private String id; // namespace + sqlId 如：org.zk.dao.UserDao.findAll
    private String sourceSql; // xml中配置的sql
    private Class<?> resultType; // 返回值类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceSql() {
        return sourceSql;
    }

    public void setSourceSql(String sourceSql) {
        this.sourceSql = sourceSql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }
}
