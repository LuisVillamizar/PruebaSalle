package com.salle.pruebaTecnica.Business.interfaces;

import com.salle.pruebaTecnica.DTO.SopaDeLetrasRequest;
import org.springframework.http.ResponseEntity;

public interface ISopaDeLetrasBusiness {
    public ResponseEntity<?> buscarPalabraEnSopa(SopaDeLetrasRequest request);
    public ResponseEntity<?> buscarPalabraEnSopaDiagonal(SopaDeLetrasRequest request); 
}
