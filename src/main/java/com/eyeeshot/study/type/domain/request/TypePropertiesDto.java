package com.eyeeshot.study.type.domain.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
public class TypePropertiesDto {
    private long seriaVersionUID = 1L;
    private String typeDescription;
    @Singular private List<String> searchableAttributes;

    @Builder
    private TypePropertiesDto(long seriaVersionUID, String typeDescription, List<String> searchableAttributes) {
        this.seriaVersionUID = seriaVersionUID;
        this.typeDescription = typeDescription;
        this.searchableAttributes = searchableAttributes;
    }
}