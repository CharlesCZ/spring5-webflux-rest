package org.czekalski.spring5webfluxrest.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document
public class Vendor {

    private String id;

    private String firstName;

    private String lastName;



}
