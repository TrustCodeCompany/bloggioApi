package com.bloggio.api.bloggio.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bloggio.api.bloggio.payload.category.UniqueCategoryDesc;

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

    private UUID categoryId;

    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 5, max = 30, message = "The size should be between 5 to 30")
    @UniqueCategoryDesc
    private String categoryDesc;

    private Integer categoryState;

}
