package com.nirup.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nirup.practice.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name of the employee cannot be blank")
    @Size(min=3,max=10,message = "NUmber of characters in name should be in the range: [3,10]")
    private String name;

    @NotBlank(message = "Email of the employee cannot be blank")
    @Email(message = "Email should be valid email")
    private String email;

    @NotNull(message = "Age of the employee cannot br blank")
    @Max(value=80,message = "Age of Employee cannot be greater than 80")
    @Min(value = 18,message = "Age of Employee cannot be less than 18")
    private Integer age;

    @NotBlank(message = "Role of the employee cannot be blank")
//    @Pattern(regexp="^(ADMIN|USER)",message = "Role of Employee can be USER or ADMIN")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "Salary of Employee should be not null")
    @Positive(message = "Salary of Employee should be positive")
    @Digits(integer = 6,fraction = 3, message = "The salary can be in the form of YYYYYY.XXX")
    private Double salary;

    @PastOrPresent(message = "DateOfJoining field in Employee cannot be in the future")
    private LocalDate dateOfJoining;


    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive;
}
