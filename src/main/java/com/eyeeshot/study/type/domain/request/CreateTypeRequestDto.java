package com.eyeeshot.study.type.domain.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
public class CreateTypeRequestDto {

    String name;
    @Singular TypePropertiesDto typeProperties;
    @Singular List<TagDto> tags;

    private CreateTypeRequestDto(String name, TypePropertiesDto typeProperties, List<TagDto> tags) {
        this.name = name;
        this.typeProperties = typeProperties;
        this.tags = tags;
    }
}
