package hrm.com.identityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Embeddable
public class RolePermissionId implements Serializable {

    @Column(name = "role_name", length = 191)
    private String roleName;

    @Column(name = "permission_name", length = 191)
    private String permissionName;

    public RolePermissionId() {}

    public RolePermissionId(String roleName, String permissionName) {
        this.roleName = roleName;
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePermissionId that)) return false;
        return Objects.equals(roleName, that.roleName)
                && Objects.equals(permissionName, that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, permissionName);
    }
}
