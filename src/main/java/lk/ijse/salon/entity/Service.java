package lk.ijse.salon.entity;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Service {
    private String service_id;
    private String service_name;
    private String service_type;
    private String price;
    private String description;
}
