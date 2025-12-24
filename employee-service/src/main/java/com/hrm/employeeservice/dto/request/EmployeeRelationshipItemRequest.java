package com.hrm.employeeservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRelationshipItemRequest {

    @NotBlank
    String fullName;

    @NotBlank
    String relationshipType; // Cha, Mẹ, Vợ, Chồng, Con...

    String phoneNumber;
    String citizenId;
}
