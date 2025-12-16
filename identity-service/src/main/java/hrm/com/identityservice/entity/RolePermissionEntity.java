package hrm.com.identityservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role_permissions")
public class RolePermissionEntity {

    @EmbeddedId
    private RolePermissionId id;

    public RolePermissionEntity() {}


}
