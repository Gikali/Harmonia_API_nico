package fr.afpa.harmonia.EcoleMusique_API.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "concert")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concert")
    private long identifiantConcert;

    @Column(name = "nom_concert", length = 50)
    private String nomConcert;
}
