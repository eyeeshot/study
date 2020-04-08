package com.eyeeshot.study.type.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeprecateTypeRequestDto {
    Boolean status;

    @Builder
    private DeprecateTypeRequestDto(Boolean status) {
        this.status = status;
    }

}
