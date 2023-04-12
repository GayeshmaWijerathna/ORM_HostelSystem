package lk.ijse.orm.hostelSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class Login {

        @Id
        private String userID;
        private String name;
        private String address;
        private String contact_no;
        private String Password;
        private String gender;
}
