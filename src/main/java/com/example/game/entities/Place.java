package com.example.game.entities;

import com.example.game.enums.PlaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "buy_price", nullable = false)
    private Integer buyPrice;

    @Column(name = "rent_price", nullable = false)
    private Integer rentPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlaceStatus status;

    @Column(name = "owner_id")
    private Long ownerId = null;

    @Column(name = "position")
    private Integer position;

}
