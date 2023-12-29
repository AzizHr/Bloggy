package com.example.bloggy.model;

import com.example.bloggy._enum.MediaType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @NotNull(message = "This field is required")
    private MediaType type;
    @NotEmpty(message = "This field is required")
    private String url;

}
