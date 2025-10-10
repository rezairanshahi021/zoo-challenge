package org.iranshahi.zoochallenge.business.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * {@code Room DTO} is using to carries Room data from/to resource layer and business layer.
 *
 * <h3>Performance Notes</h3>
 * <ul>
 *     <li>This is an immutable object, so will have less effort on JVM</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
public record RoomDto(String id,
                      @NotBlank(message = "Title must not be empty")
                      @Size(min = 2, max = 50, message = "Title length must be between 2 and 50 characters")
                      String title) {
}
