package lk.ijse.salon.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class ServiceTm {
    private String s_id;
    private String name;
    private String price;
    private String type;
    private String description;
}
