package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.response.EmployeeWorkHistoryResponse;
import com.hrm.employeeservice.mapper.EmployeeWorkHistoryMapper;
import com.hrm.employeeservice.repository.EmployeeRepository;
import com.hrm.employeeservice.repository.EmployeeWorkHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeWorkHistoryService {

    EmployeeWorkHistoryRepository historyRepository;
    EmployeeRepository employeeRepository;
    EmployeeWorkHistoryMapper historyMapper;

    public List<EmployeeWorkHistoryResponse> getByEmployee(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy nhân viên"
            );
        }

        return historyRepository
                .findByEmployee_IdOrderByFromDateDesc(employeeId)
                .stream()
                .map(historyMapper::toResponse)
                .toList();
    }
}
