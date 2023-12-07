package com.project.sprojectbe.dto;

import com.project.sprojectbe.models.Libro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDTO {

    private Long id;
    private String titolo;
    private String autore;
    private String editore;
    private String categoria;
    private String annoPubblicazione;
    private String descrizione;

    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;

    public static LibroDTO buildLibroDTOFromLibro(Libro l) {
        return LibroDTO.builder().id(l.getId()).titolo(l.getTitolo()).autore(l.getAutore()).editore(l.getEditore()).annoPubblicazione(l.getAnnoPubblicazione()).descrizione(l.getDescrizione()).build();
    }

    public static Libro buildLibroFromLibroDTO(LibroDTO libroDTO) {
        return Libro.builder().id(libroDTO.getId()).titolo(libroDTO.getTitolo()).autore(libroDTO.getAutore()).editore(libroDTO.getEditore()).annoPubblicazione(libroDTO.getAnnoPubblicazione()).descrizione(libroDTO.getDescrizione()).build();
    }

    public static List<LibroDTO> buildLibriDTOFromListLibri(List<Libro> listLibri) {
        List<LibroDTO> listLibriDTO = new ArrayList<>();
        for (Libro l : listLibri) {
            listLibriDTO.add(LibroDTO.buildLibroDTOFromLibro(l));
        }
        return listLibriDTO;
    }


}
