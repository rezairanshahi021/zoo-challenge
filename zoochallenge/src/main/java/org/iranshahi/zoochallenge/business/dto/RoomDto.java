package org.iranshahi.zoochallenge.business.dto;


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
                      String title) {
}
