package com.eyeeshot.study.type.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    private String key;
    private String value;

    @Builder
    private TagDto(String key, String value) {
        this.key = key;
        this.value = value;
    }
}