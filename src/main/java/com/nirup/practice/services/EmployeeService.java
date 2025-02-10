package com.nirup.practice.services;

import com.nirup.practice.dto.EmployeeDTO;
import com.nirup.practice.entities.EmployeeEntity;
import com.nirup.practice.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper){
        this.employeeRepository=employeeRepository;
        this.modelMapper=modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
//        EmployeeEntity employeeEntity=employeeRepository.findById(id).orElse(null);
//        return modelMapper.map(employeeEntity,EmployeeDTO.class);
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));
    }


    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities=employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS=employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class)).collect(Collectors.toList());
        return employeeDTOS;
    }


    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
       EmployeeEntity toSaveEntity=modelMapper.map(inputEmployee,EmployeeEntity.class);
       EmployeeEntity employeeEntity=employeeRepository.save(toSaveEntity);
       return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity=modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public boolean isExistsByEmployeeId(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        boolean exists=isExistsByEmployeeId(employeeId);
        if(!exists)return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean exists=isExistsByEmployeeId(employeeId);
        if(!exists)return null;
        EmployeeEntity employeeEntity=employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{
            Field fieldToUpdated=ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldToUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdated,employeeEntity,value);
        });

        return  modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);

    }
}
