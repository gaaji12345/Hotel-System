package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Roomdto {
    private String id;
    private String details;
    private String roomType;
    private Double price;
}
