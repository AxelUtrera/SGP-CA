package businesslogic;

import java.sql.SQLException;

public class BusinessLogicException extends SQLException {

    public BusinessLogicException(String message, SQLException sqlException){
        super(message);
    }

    public BusinessLogicException(String message){
        super(message);
    }
}