package com.api.rest.repository;

import com.api.rest.model.Encuesta;
import com.api.rest.model.Opcion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionRepository extends CrudRepository<Opcion, Long> {
}
