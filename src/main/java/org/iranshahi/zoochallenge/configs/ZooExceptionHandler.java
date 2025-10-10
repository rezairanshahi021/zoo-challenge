package org.iranshahi.zoochallenge.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.iranshahi.zoochallenge.exceptions.DuplicateRoomTitleException;
import org.iranshahi.zoochallenge.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.Map;


/**
 *
 * <p>This exception indicates a client-side error (HTTP 404)
 * and is handled by the {@link ZooExceptionHandler}, which converts it
 * to a standardized {@code ProblemDetail} response according to RFC 7807.
 *
 * <h2>Example:</h2>
 * <pre>{@code
 * throw new AnimalNotFoundException("a123");
 * }</pre>
 * <p>
 * produces:
 * <pre>{@code
 * {
 *   "type": "https://zoo.example.com/errors/animal-not-found",
 *   "title": "Animal not found",
 *   "status": 404,
 *   "detail": "Animal with id a123 not found",
 *   "errorCode": "ANIMAL_NOT_FOUND"
 * }
 * }</pre>
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @see ZooException
 * @since 9 Oct 2025
 */

@RestControllerAdvice
public class ZooExceptionHandler {

    /**
     * Handles all domain-level exceptions derived from {@link ZooException}.
     *
     * <p>Returns HTTP 400 (Bad Request) by default, unless overridden by a
     * more specific handler.
     *
     * @param ex  the domain exception thrown by business logic.
     * @param req the current web request.
     * @return a standardized {@link ProblemDetail} response body.
     */
    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ProblemDetail> handleZooExceptions(ZooException ex, WebRequest req) {
        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ex.getClass().getSimpleName());
        problem.setType(URI.create("https://zoo.example.com/errors/" + ex.getErrorCode().toLowerCase()));
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorCode", ex.getErrorCode());
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }


    @ExceptionHandler(DuplicateRoomTitleException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateRoomTitleException ex) {
        return ResponseEntity.status(409).body(Map.of("error", ex.getMessage()));
    }

    /**
     * Fallback handler for all unexpected exceptions.
     *
     * <p>This method ensures that even unforeseen runtime errors
     * are safely serialized into a structured JSON response, preventing
     * sensitive details from leaking to the client.
     *
     * @param ex  the unhandled exception.
     * @param req the HTTP request during which the exception occurred.
     * @return a generic {@link ProblemDetail} with HTTP 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create("https://zoo.example.com/errors/internal"));
        pd.setTitle("Internal error");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }
}
