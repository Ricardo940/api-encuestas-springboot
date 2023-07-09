package com.api.rest.controller.v1;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Encuesta;
import com.api.rest.repository.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("EncuestaControllerV1")
@RequestMapping("/api/v1/encuestas")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping
    public ResponseEntity<Iterable<Encuesta>> listarEncuestas(){
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEncuesta(@PathVariable Long id){
        verifyEncuesta(id);
        Optional<Encuesta> encuesta = encuestaRepository.findById(id);
        return new ResponseEntity<>(encuesta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crearEncuesta(@Valid @RequestBody Encuesta encuesta){
        encuesta = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(encuesta.getId()).toUri();
        httpHeaders.setLocation(newEncuestaUri);

        return new ResponseEntity<>(null,httpHeaders,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEncuesta(@Valid @PathVariable Long id, @RequestBody Encuesta encuesta){
        verifyEncuesta(id);
        encuesta.setId(id);
        encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEncuesta(@PathVariable Long id){
        verifyEncuesta(id);
        encuestaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    protected void verifyEncuesta(Long encuestaId){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);
        if(!encuesta.isPresent()){
            throw new ResourceNotFoundException("Encuesta con el ID : " + encuestaId + " no encontrada");
        }
    }



}
