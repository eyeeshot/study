package com.eyeeshot.study.type.domain.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
public class CreateTypeRequestDto {

    private String name;
    @Singular private TypePropertiesDto typeProperties;
    @Singular private List<TagDto> tags;

    @Builder
    private CreateTypeRequestDto(String name, TypePropertiesDto typeProperties, List<TagDto> tags) {
        this.name = name;
        this.typeProperties = typeProperties;
        this.tags = tags;
    }
}
