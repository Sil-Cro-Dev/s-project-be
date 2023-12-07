package com.project.sprojectbe.service;

import com.project.sprojectbe.dto.LibroDTO;
import com.project.sprojectbe.models.Libro;
import com.project.sprojectbe.repository.LibroRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public List<Libro> getAll() {
        return (List<Libro>) libroRepository.findAll();
    }

    @Transactional
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    public void importXlsx(MultipartFile file) throws IOException {
        log.info("inizio save dati file xlsx -> " + file.getOriginalFilename());

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                Libro libro = new Libro();

                if (row.getCell(0) == null)
                    continue;
                libro.setTitolo(row.getCell(0).getStringCellValue());

                if (row.getCell(1) != null)
                    libro.setAutore(row.getCell(1).getStringCellValue());

                if (row.getCell(2) != null)
                    libro.setEditore(row.getCell(2).getStringCellValue());

                if (row.getCell(3) != null)
                    libro.setCategoria(row.getCell(3).getStringCellValue());

                if (row.getCell(4) != null)
                    libro.setAnnoPubblicazione(row.getCell(4).getStringCellValue());

                if (row.getCell(5) != null)
                    libro.setDescrizione(row.getCell(5).getStringCellValue());

                log.debug("libro -> " + libro);

                libroRepository.save(libro);

            }

        }

        log.info("fine save dati file xlsx -> " + file.getOriginalFilename());
    }


    @Transactional
    public Page<Libro> searchAndPaginate(LibroDTO filtro) {

        Specification<Libro> specificationCriteria = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();


            if (!StringUtils.isEmpty(filtro.getTitolo()))
                predicates.add(cb.like(root.get("titolo"), "%" + filtro.getTitolo() + "%"));


            if (!StringUtils.isEmpty(filtro.getAutore()))
                predicates.add(cb.like(root.get("autore"), "%" + filtro.getAutore() + "%"));


            if (!StringUtils.isEmpty(filtro.getEditore()))
                predicates.add(cb.like(root.get("editore"), "%" + filtro.getEditore() + "%"));


            if (!StringUtils.isEmpty(filtro.getCategoria()))
                predicates.add(cb.equal(root.get("categoria"), filtro.getCategoria()));

            if (!StringUtils.isEmpty(filtro.getAnnoPubblicazione()))
                predicates.add(cb.like(root.get("annoPubblicazione"), "%" + filtro.getAnnoPubblicazione() + "%"));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Pageable paging = PageRequest.of(filtro.getPageNo(), filtro.getPageSize(), Sort.by(Sort.Direction.fromString(filtro.getSortDirection()), filtro.getSortBy()));

        return libroRepository.findAll(specificationCriteria, paging);
    }


}
