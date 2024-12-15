package dsi.soutenance.service;

import java.util.List;

public interface AbstractService<T> {
    Long save(T d);

    void saveAll(List<T> y);

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    void deleteAll(List<T> y);
}
