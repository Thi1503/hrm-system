package hrm.com.identityservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class RoleEntity {

    @Id
    @Column(length = 191)
    private String name;

    @Column(length = 255)
    private String description;

  
}
