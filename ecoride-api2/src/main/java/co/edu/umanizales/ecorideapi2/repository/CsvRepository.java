package co.edu.umanizales.ecorideapi2.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CsvRepository<T> {
    List<T> findAll() throws IOException;
    Optional<T> findById(String id) throws IOException;
    T save(T entity) throws IOException;
    void deleteById(String id) throws IOException;
    void saveAll(List<T> entities) throws IOException;
}
