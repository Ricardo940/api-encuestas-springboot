package com.api.rest.controller.v2;

import com.api.rest.model.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("VotoControllerV2")
@RequestMapping("/api/v2/votos")
public class VotoController {

    @Autowired
    private VotoRepository votoRepository;

    @PostMapping("/encuestas/{encuestaId}")
    public ResponseEntity<?> crearVoto(@PathVariable Long encuestaId, @RequestBody Voto voto){
        voto = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newVotoUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(voto.getId()).toUri();
        httpHeaders.setLocation(newVotoUri);

        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/encuestas/{encuestaId}")
    public Iterable<Voto> listarVotos(@PathVariable Long encuestaId){
        return votoRepository.findByEncuesta(encuestaId);
    }
}
