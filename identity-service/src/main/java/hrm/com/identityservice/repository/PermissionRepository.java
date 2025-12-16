package hrm.com.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hrm.com.identityservice.entity.PermissionEntity;

@Repository
public interface PermissionRepository
        extends JpaRepository<PermissionEntity, String> {
}
