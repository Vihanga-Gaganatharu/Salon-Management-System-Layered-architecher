package lk.ijse.salon.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class CustomerTm {
    private String id;
    private String name;
    private String nic;
    private String address;
    private String phoneno;
    private String gender;
}
