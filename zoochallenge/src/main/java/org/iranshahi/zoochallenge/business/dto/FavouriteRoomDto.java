package org.iranshahi.zoochallenge.business.dto;

/**
 * {@code Favourite Room DTO} is using to carries Animal's favourite data to resource layer and business layer.
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
public record FavouriteRoomDto(String roomId,
                               String title,
                               long count) {
}
