package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMyInfoRequest {

    String fullName;
    Gender gender;
    LocalDate dateOfBirth;

    String phoneNumber;
    String email;
    String address;

    String avatarUrl;
}
