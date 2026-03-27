package com.alpha.core.gym_management_system.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachRegistrationRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String number;

    @NotBlank
    @Email
    private String email;

    private Long gymId;

    private Integer age;

    @Size(max = 20)
    private String gender;

    private Integer yearsOfExperience;

    @Size(max = 2000)
    private String otherDetails;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}
