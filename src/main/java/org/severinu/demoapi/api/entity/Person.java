package org.severinu.demoapi.api.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Person {
    private int id;
    private String name;
    private String surname;
}
