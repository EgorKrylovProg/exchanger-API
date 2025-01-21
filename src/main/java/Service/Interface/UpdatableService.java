package Service.Interface;

import Exceptions.DatabaseAccessException;
import Exceptions.NoDataFoundException;

import java.util.Optional;

public interface UpdatableService <T, R> extends Service <T, R> {

    R update(R r) throws DatabaseAccessException, NoDataFoundException;

}
