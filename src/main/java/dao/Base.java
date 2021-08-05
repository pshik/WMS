package dao;

public interface Base {
    void reloadAll();
    void reloadTable(Class theClass);
    void save(Object entity);
    void removeById(Class theClass, Long id);
    void close();
    Object getObjectById(Class theClass, long id);
    Object getObjectByField(Class theClass,String value,String field);
    void update( Object entity);

}
