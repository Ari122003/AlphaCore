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
public class AdminRegistrationRequest {

    @NotBlank
    @Size(max = 100)
    private String gymName;

    @NotBlank
    private String gymAddress;

    @NotBlank
    @Size(max = 20)
    private String contactNumber;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}
