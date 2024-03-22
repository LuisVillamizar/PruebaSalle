package com.salle.pruebaTecnica.Controller;

import com.salle.pruebaTecnica.Business.interfaces.ISopaDeLetrasBusiness;
import com.salle.pruebaTecnica.DTO.SopaDeLetrasRequest;
import com.salle.pruebaTecnica.DTO.SopaDeLetrasResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Controller
@Slf4j
@RestController
public class SopaDeLetrasController {

    @Autowired
    private ISopaDeLetrasBusiness sopaDeLetrasBusiness;

    //RUTA POR LA CUAL SE VA A CONSUMIR EL API http://localhost:8090/search-horandvert
    @PostMapping("/search-horandvert")
    public ResponseEntity<?> buscarPalabraEnSopa(@RequestBody SopaDeLetrasRequest request) {
        return sopaDeLetrasBusiness.buscarPalabraEnSopa(request);
    }
    
    //RUTA POR LA CUAL SE VA A CONSUMIR EL API http://localhost:8090/search
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SopaDeLetrasRequest request) {
       return sopaDeLetrasBusiness.buscarPalabraEnSopaDiagonal(request);
    }
}
