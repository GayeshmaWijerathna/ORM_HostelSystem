package lk.ijse.orm.hostelSystem.view.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTM {

    private String userID;
    private String name;
    private String address;
    private String contact_no;
    private String Password;
    private String gender;
}
