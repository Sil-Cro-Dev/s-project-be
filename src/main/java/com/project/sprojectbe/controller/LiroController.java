package com.project.sprojectbe.controller;

import com.project.sprojectbe.dto.LibroDTO;
import com.project.sprojectbe.models.Libro;
import com.project.sprojectbe.service.LibroService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "api/libro", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LiroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/getLibri")
    public ResponseEntity<List<LibroDTO>> getAllLibri() {
        log.info("called GET -> /api/libro/getLibri");
        List<LibroDTO> results = LibroDTO.buildLibriDTOFromListLibri(libroService.getAll());
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/getLibri")
    public ResponseEntity<Page<Libro>> getLibriFiltered(@RequestBody LibroDTO libroDTO) {
        log.info("called POST -> /api/libro/getLibri");
        Page<Libro> results = libroService.searchAndPaginate(libroDTO);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/insertLibri")
    public ResponseEntity<Libro> insertLibro(@RequestBody LibroDTO libroDTO) {
        log.info("called POST -> /api/libro/insertLibri");
        return ResponseEntity.ok().body(libroService.save(LibroDTO.buildLibroFromLibroDTO(libroDTO)));
    }

    @PostMapping("/importXlsxLibri")
    public ResponseEntity<String> importXlsxLibri(@RequestParam("file") MultipartFile file) {
        log.info("called POST -> /api/libro/importXlsxLibri");

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("il file è vuoto");
        }
        try {
            libroService.importXlsx(file);
            return ResponseEntity.ok().body("Import avvenuto con successo");
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("Errore durante l'import");
        }

    }

}
