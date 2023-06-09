package lk.ijse.orm.hostelSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {

    @Id
    @Column(columnDefinition = "VARCHAR(250)")
    private String student_id;
    private String name;
    private String address;
    private String contact_no;
    private LocalDate dob;
    private String gender;

    @OneToMany(mappedBy = "student_id", fetch = FetchType.EAGER)
    private List<Reservation> studentList = new ArrayList<>();

    public Student(String student_id, String name, String address, String contact_no, LocalDate dob, String gender) {
        this.student_id = student_id;
        this.name = name;
        this.address = address;
        this.contact_no = contact_no;
        this.dob = dob;
        this.gender = gender;
    }
}
