package hrm.com.identityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "role_name", length = 191)
    private String roleName;

    public UserRoleId() {}

    public UserRoleId(String userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }

    public String getUserId() { return userId; }
    public String getRoleName() { return roleName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleId that = (UserRoleId) o;

        return Objects.equals(userId, that.userId)
                && Objects.equals(roleName, that.roleName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(userId, roleName);
    }
}
