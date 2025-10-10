package org.iranshahi.zoochallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

/**
 *
 * <h3>Annotation Notes</h3>
 * <ul>
 *     <li>
 *         <b>EnableSpringDataWebSupport</b>: Tells Spring Data to serialize Page as a stable DTO structure (with content, page, totalElements, etc.)
 *     </li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 10 Oct 2025
 *
 */
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ZoochallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoochallengeApplication.class, args);
    }

}
