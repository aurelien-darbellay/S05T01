package aDarbellay.s05.t1.exception.handlers;

import aDarbellay.s05.t1.exception.EntityNotFoundException;
import aDarbellay.s05.t1.exception.IllegalActionException;
import aDarbellay.s05.t1.exception.UntimelyActionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
        String code = "DB_ERROR:";
        if (ex instanceof DuplicateKeyException) {
            code = "DUPLICATE_KEY_EXCEPTION";
        }
        return ResponseEntity.status(500).body(new ErrorResponse(code, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse("ENTITY_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<ErrorResponse> IllegalActionException(IllegalActionException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse("ILLEGAL_ACTION", ex.getMessage()));
    }

    @ExceptionHandler(UntimelyActionException.class)
    public ResponseEntity<ErrorResponse> UntimelyActionException(UntimelyActionException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse("UNTIMELY_ACTION", ex.getMessage()));
    }
    
    public record ErrorResponse(String code, String message) {
    }

}
