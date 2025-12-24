package com.hrm.employeeservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRelationshipResponse {

    Long id;
    String fullName;
    String relationshipType;
    String phoneNumber;
    String citizenId;
    LocalDateTime createdAt;
}
