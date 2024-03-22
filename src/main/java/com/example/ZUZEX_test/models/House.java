package com.example.ZUZEX_test.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house")
@Getter
@Builder
@EqualsAndHashCode
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String address;
    @Setter
    private Long ownerId;
}
