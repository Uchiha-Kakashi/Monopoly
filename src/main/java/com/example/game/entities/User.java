package com.example.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    // This field denotes if the user is currently associated with a game
    @Column(name = "is_playing", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPlaying;

    @Column(name = "is_host", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isHost;

}
