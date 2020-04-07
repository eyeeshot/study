package com.eyeeshot.study.type.domain.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTypeRequestDto {

    String name;
    TypePropertiesDto typeProperties;
    List<TagDto> tags;

    private CreateTypeRequestDto(String name, TypePropertiesDto typeProperties, List<TagDto> tags) {
        this.name = name;
        this.typeProperties = typeProperties;
        this.tags = tags;
    }
}
