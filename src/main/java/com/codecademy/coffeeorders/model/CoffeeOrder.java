package com.codecademy.coffeeorders.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor // JPA requires a no-arg constructor
public class CoffeeOrder {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Blend cannot be blank")
    private String blend;

    @NotNull(message = "Size must be specified")
    @Enumerated(EnumType.STRING) // Stores the enum as a string ("TALL") instead of a number (1)
    private DrinkSize size;

    @NotBlank(message = "Customer name cannot be blank")
    private String customer;

    // Use a primitive boolean for a non-nullable flag
    private boolean fulfilled = false;
}
