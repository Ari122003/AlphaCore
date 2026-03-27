package com.alpha.core.gym_management_system.Coaches.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coaches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String number;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "gym_id")
    private Long gymId;

    private Integer age;

    @Column(length = 20)
    private String gender;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "other_details", columnDefinition = "TEXT")
    private String otherDetails;
}
