package com.bloggio.api.bloggio.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String categoryId;

    // Validar que la categoria no sea repetida (UniqueCategory)
    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 30, max = 200, message = "The size should be between 30 to 200")
    private String categoryDesc;

    @NotNull
    private boolean categoryState;

}
