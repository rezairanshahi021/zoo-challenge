package org.iranshahi.zoochallenge.business.dto;

import java.time.LocalDate;
import java.util.Set;

/**
 * {@code Animal DTO} is using to carries Animal data from/to resource layer and business layer.
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
public record AnimalDto(
        String id,
        String title,
        LocalDate located,
        String roomId,
        Set<String> favouriteRoomIds

) {
}
