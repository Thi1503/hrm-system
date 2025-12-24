package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.EmployeeContractCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeContractUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeContractResponse;
import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.EmployeeContract;
import com.hrm.employeeservice.mapper.EmployeeContractMapper;
import com.hrm.employeeservice.repository.EmployeeContractRepository;
import com.hrm.employeeservice.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeContractService {

    EmployeeContractRepository contractRepository;
    EmployeeRepository employeeRepository;
    EmployeeContractMapper contractMapper;

    /** CREATE */
    public EmployeeContractResponse create(EmployeeContractCreateRequest request) {

        if (contractRepository.existsByContractNumber(request.getContractNumber())) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Số hợp đồng đã tồn tại"
            );
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy nhân viên"
                ));

        EmployeeContract contract = new EmployeeContract();
        contract.setEmployee(employee);
        contract.setContractNumber(request.getContractNumber());
        contract.setContractType(request.getContractType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setSalaryIndex(request.getSalaryIndex());
        contract.setSalaryBase(request.getSalaryBase());
        contract.setPosition(request.getPosition());
        contract.setContractUrl(request.getContractUrl());
        contract.setStatus(request.getStatus());
        contract.setCreatedAt(LocalDateTime.now());

        return contractMapper.toResponse(
                contractRepository.save(contract)
        );
    }

    /** UPDATE */
    public EmployeeContractResponse update(Long id, EmployeeContractUpdateRequest request) {

        EmployeeContract contract = contractRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy hợp đồng"
                ));

        contract.setContractType(request.getContractType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setSalaryIndex(request.getSalaryIndex());
        contract.setSalaryBase(request.getSalaryBase());
        contract.setPosition(request.getPosition());
        contract.setContractUrl(request.getContractUrl());
        contract.setStatus(request.getStatus());

        return contractMapper.toResponse(
                contractRepository.save(contract)
        );
    }

    /** GET DETAIL */
    public EmployeeContractResponse getDetail(Long id) {
        return contractMapper.toResponse(
                contractRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy hợp đồng"
                        ))
        );
    }

    /** GET LIST */
    public List<EmployeeContractResponse> getList() {
        return contractRepository.findAll()
                .stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    /** GET LIST BY EMPLOYEE */
    public List<EmployeeContractResponse> getByEmployee(Long employeeId) {
        return contractRepository.findByEmployee_Id(employeeId)
                .stream()
                .map(contractMapper::toResponse)
                .toList();
    }

    /** DELETE */
    public void delete(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy hợp đồng"
            );
        }
        contractRepository.deleteById(id);
    }
}
