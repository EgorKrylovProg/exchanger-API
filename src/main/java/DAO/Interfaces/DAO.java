package DAO.Interfaces;

import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.util.List;
import java.util.Optional;

public interface DAO <T, R> {

    List<R> getAll() throws DatabaseAccessException;
    Optional<R> get(T t) throws DatabaseAccessException;
    Optional<R> getById (Integer id) throws DatabaseAccessException;
    R save(R r) throws DataDuplicationException, DatabaseAccessException, NoDataFoundException;

}
