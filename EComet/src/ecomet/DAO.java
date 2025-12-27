package ecomet;

import java.util.List;

public interface DAO<T> {
    T findById(int id) throws AppException;
    List<T> findAll() throws AppException;
    void save(T obj) throws AppException;
    void update(T obj) throws AppException;
    void delete(int id) throws AppException;
}
