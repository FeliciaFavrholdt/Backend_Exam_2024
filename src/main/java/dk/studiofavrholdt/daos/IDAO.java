package dk.studiofavrholdt.daos;

import dk.studiofavrholdt.dtos.ItemDTO;
import dk.studiofavrholdt.dtos.StudentDTO;

import java.util.List;
import java.util.Optional;

/***
 * 2.4.2 Define a generic interface IDAO with CRUD operations.
 */

public interface IDAO<T, I> {

    /**
     * Optional is a container object which may or may not contain a non-null value.
     * If a value is present, isPresent() will return true and get() will return the value.
     */
    Optional<T> get(I i);

    List<T> getAll();

    T create(T t);

    ItemDTO create(ItemDTO itemDTO, StudentDTO studentDTO);

    T update(I i, T t);

    void delete(I i);
}