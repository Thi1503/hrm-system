package hrm.com.identityservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hrm.com.identityservice.entity.UserRoleEntity;
import hrm.com.identityservice.entity.UserRoleId;

@Repository
public interface UserRoleRepository
        extends JpaRepository<UserRoleEntity, UserRoleId> {

    List<UserRoleEntity> findByIdUserId(String userId);
}
