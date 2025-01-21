package Service.Interface;

import Exceptions.DataDuplicationException;
import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.util.List;

public interface Service <T, R> {

    List<R> readAll() throws DatabaseAccessException;
    R read(T t) throws DatabaseAccessException, NoDataFoundException;
    R create(R r) throws DataDuplicationException, DatabaseAccessException, NoDataFoundException;

}
