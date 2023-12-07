package com.project.sprojectbe.repository;

import com.project.sprojectbe.models.Libro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends PagingAndSortingRepository<Libro, Long>, JpaSpecificationExecutor<Libro>, CrudRepository<Libro, Long> {
}
