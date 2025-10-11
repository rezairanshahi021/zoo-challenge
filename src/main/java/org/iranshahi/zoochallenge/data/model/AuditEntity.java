package org.iranshahi.zoochallenge.data.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter@Setter
public abstract class AuditEntity {


    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant updated;
}
