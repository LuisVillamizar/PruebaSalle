
package com.salle.pruebaTecnica.DTO;

import lombok.Data;

@Data
//CLASE DOMINO CUENTA CON LOS GET Y SET INCLUIDOS GRACIAS AL @Data
public class SopaDeLetrasRequest {
    private String searchword;
    private int rows;
    private String word;

  
}
