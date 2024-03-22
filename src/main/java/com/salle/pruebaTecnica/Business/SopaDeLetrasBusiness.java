package com.salle.pruebaTecnica.Business;

import com.salle.pruebaTecnica.Business.interfaces.ISopaDeLetrasBusiness;
import com.salle.pruebaTecnica.DTO.SopaDeLetrasRequest;
import com.salle.pruebaTecnica.DTO.SopaDeLetrasResponse;
import com.salle.pruebaTecnica.DTO.SopaDeLetrasResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author LuisVillamizar
 */
@Component
@Slf4j
public class SopaDeLetrasBusiness implements ISopaDeLetrasBusiness {

    private Integer startRow;
    private Integer startCol;
    @Override
    public ResponseEntity<?> buscarPalabraEnSopa(SopaDeLetrasRequest request) {
        if (validateRequest(request)) {
            char[][] matrix = convertirAMatrix(request.getSearchword(), request.getRows());
            boolean contains = buscarPalabra(matrix, request.getWord());
            SopaDeLetrasResponse result = new SopaDeLetrasResponse(request.getSearchword(), request.getRows(), request.getWord(), contains, startRow, startCol);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new SopaDeLetrasResponseError("Error de validacion"), HttpStatus.FORBIDDEN);
        }
    }
    
   
    @Override
    public ResponseEntity<?> buscarPalabraEnSopaDiagonal(SopaDeLetrasRequest request) {
        if (validateRequest(request)) {
            char[][] matrix = convertirAMatrix(request.getSearchword(), request.getRows());
            boolean contains = buscarPalabra(matrix, request.getWord()) 
                || buscarPalabraEnSopaDiagonal(matrix, request.getWord());
            SopaDeLetrasResponse result = new SopaDeLetrasResponse(request.getSearchword(), request.getRows(), request.getWord(), contains, startRow, startCol);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new SopaDeLetrasResponseError("Error de validacion"), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Valida que la solicitud no llegue vacia
     * @param request
     * @return
     */
    private boolean validateRequest(SopaDeLetrasRequest request) {
        log.info("Validando solicitud...");
        return     request != null
                && request.getSearchword() != null
                && request.getWord() != null
                && request.getRows() > 0;
    }

    /**
     * Convierte el texto a matrix
     * @param palabraMatrix
     * @param filas
     * @return
     */
    private char[][] convertirAMatrix(String palabraMatrix, int filas) {
        log.info("Convirtiendo texto a matrix");
        int cols = palabraMatrix.length() / filas;
        char[][] matrix = new char[filas][cols];
        int k = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = palabraMatrix.charAt(k++);
            }
        }
        return matrix;
    }

    /**
     * Busca la palabra en la sopa de letras
     * @param matrix
     * @param word
     * @return
     */
    private boolean buscarPalabra(char[][] matrix, String word) {
        log.info("Buscando palabra...");
        int rows = matrix.length;
        int cols = matrix[0].length;
        int wordLength = word.length();

        // BUSCA HORIZONTALMENTE
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - wordLength; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < wordLength; k++) {
                    sb.append(matrix[i][j + k]);
                }
                if (sb.toString().equals(word)) {
                    startRow = i;
                    startCol = j;
                    log.info("La palabra esta horizontalmente");
                    return true;
                }
            }
        }

        // BUSCA VERTICALMENTE
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i <= rows - wordLength; i++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < wordLength; k++) {
                    sb.append(matrix[i + k][j]);
                }
                if (sb.toString().equals(word)) {
                    startRow = i;
                    startCol = j;
                    log.info("La palabra esta verticalmente");
                    return true;
                }
            }
        }
        startRow = null;
        startCol = null;
        log.info("La palabra no esta");
        return false;
    }
    
    /**
     * Busca la palabra en la sopa de letras diagonalmente
     * @param matrix
     * @param word
     * @return
     */
    private  boolean buscarPalabraEnSopaDiagonal(char[][] matrix, String word) {
        log.info("Buscando palabra...");
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            
            for (int j = 0; j < cols; j++) {
                
                // VALIDA SI ENCUENTA LA PRIMERA LETRA
                if (matrix[i][j] == word.charAt(0)) {
                    
                    // VERIFICA LA DIAGONAL HACIA ABAJO
                    if (j + word.length() <= cols && i + word.length() <= rows) {
                     
                        boolean found = true;
                        for (int k = 1; k < word.length(); k++) {
                         
                            if (matrix[i + k][j + k] != word.charAt(k)) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            startRow = i;
                            startCol = j;
                            log.info("La palabra  esta diagonal abajo");
                            return true;
                        }
                    }
                    
                    // VERIFICA LA DIAGONAL HACIA ARRIBA
                    if (j + word.length() <= cols && i - word.length() >= -1) {
                        boolean found = true;
                        for (int k = 1; k < word.length(); k++) {
                            if (matrix[i - k][j + k] != word.charAt(k)) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            startRow = i;
                            startCol = j;
                            log.info("La palabra  esta diagonal arriba");
                            return true;
                        }
                    }
                }
            }
        }
       
        startRow = null;
        startCol = null;
        log.info("La palabra no esta");
       
        return false;
    }
}
