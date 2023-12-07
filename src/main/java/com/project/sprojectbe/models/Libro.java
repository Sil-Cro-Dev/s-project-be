package com.project.sprojectbe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "libro")
public class Libro {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titolo")
    private String titolo;
    @Column(name = "autore")
    private String autore;
    @Column(name = "editore")
    private String editore;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "anno_pubblicazione")
    private String annoPubblicazione;
    @Column(name = "descrizione", length = 1000)
    private String descrizione;

//    private Posizione posizione;

}