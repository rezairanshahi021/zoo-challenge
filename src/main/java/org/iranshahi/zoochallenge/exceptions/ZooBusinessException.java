package org.iranshahi.zoochallenge.exceptions;

import lombok.Getter;


/**
 * Base class for all domain-specific exceptions in the Zoo challenge app .
 * <p>
 * This abstract class serves as the parent of all custom exceptions thrown
 * by business and domain layers (e.g., {@link AnimalNotFoundException}, {@link RoomNotFoundException}).
 * <p>
 * It carries a semantic {@code errorCode} field that can be used to uniquely
 * identify the type of business error in logs, telemetry systems, or API responses.
 * <p>
 * All {@code ZooException} subclasses are considered <b>runtime exceptions</b>
 * and are automatically handled by the {@link org.iranshahi.zoochallenge.configs.ZooExceptionHandler}.
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @since 9 Oct 2025
 */

public sealed class ZooBusinessException extends RuntimeException
        permits AnimalNotFoundException,
        RoomNotFoundException,
        DuplicateRoomTitleException,
        CategoryMismatchException,
        RoomOutOfSpaceException,
        ConcurrencyException,
        AnimalNotPlacedException

{

    @Getter
    private final String errorCode;

    public ZooBusinessException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.errorCode = exceptionCode.name();
    }
}
