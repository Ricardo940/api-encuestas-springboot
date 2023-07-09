package com.api.rest.controller.v1;

import com.api.rest.dto.OpcionCount;
import com.api.rest.dto.VotoResult;
import com.api.rest.model.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeResultControlerV1")
@RequestMapping("/api/v1/resultados/encuestas")
public class ComputeResultController {

    @Autowired
    private VotoRepository votoRepository;

    @GetMapping
    public ResponseEntity<?> calcularResultados(@RequestParam Long encuestaId){
        VotoResult votoResult = new VotoResult();

        Iterable<Voto> votos = votoRepository.findByEncuesta(encuestaId);


        //algoritmo para contar votos
        int totalVotos = 0;
        Map<Long, OpcionCount> tempMap = new HashMap<>();

        for (Voto v:votos){
            totalVotos ++;
            OpcionCount opcionCount = tempMap.get(v.getOpcion().getId());
            if(opcionCount == null){
                opcionCount = new OpcionCount();
                opcionCount.setOpcionId(v.getOpcion().getId());
                tempMap.put(v.getOpcion().getId(),opcionCount);
            }
            opcionCount.setCount(opcionCount.getCount()+1);
        }

        votoResult.setTotalVotos(totalVotos);
        votoResult.setResultados(tempMap.values());

        return new ResponseEntity<>(votoResult, HttpStatus.OK);
    }
}
