package com.hrm.employeeservice.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPositionResponse {
    private Long id;

    private String name;

    private Integer level;

    private LocalDateTime createdAt;
}
