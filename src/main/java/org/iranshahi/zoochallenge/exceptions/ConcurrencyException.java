package org.iranshahi.zoochallenge.exceptions;

/**
 *
 * @author Reza Iranshahi
 * @since 11 Oct 2025
 * */
public final class ConcurrencyException extends ZooBusinessException {
    public ConcurrencyException() {
        super("Too many concurrent modifications; please retry", ExceptionCode.CONCURRENT_ERROR);
    }
}
