
package com.salle.pruebaTecnica.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//CLASE DOMINO CUENTA CON LOS GET Y SET INCLUIDOS GRACIAS AL @Data
public class SopaDeLetrasResponse {

        private String searchword;
        private int rows;
        private String word;
        private boolean contains;
        private Integer startRow;
        private Integer startCol;


}
