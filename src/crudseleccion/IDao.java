package crudseleccion;

import java.util.List;

/**
 *
 * @author alsorc
 * @param <T>
 */
public interface IDao<T> {
    
    public boolean insert(T t);
    public boolean delete(T t);
    public boolean update(T t);
    public List<T> getRecords();
    public T getOneRecord(T t);
    
    
}
