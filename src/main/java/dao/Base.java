package dao;

public interface Base {
    void reloadAll();
    void reloadTable(String tableName);
    void save(String tableName,Object entity);
    void remove(String tableName,Long id);
    void close();
    //void findById(String tableName,Long id);
    //void findByField(String tableName, String field);

}
