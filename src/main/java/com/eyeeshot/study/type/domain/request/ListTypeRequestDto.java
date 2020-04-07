package com.eyeeshot.study.type.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ListTypeRequestDto {

    private String name;
    private String nextToken;
    private Integer maxResults;

    @Builder
    private ListTypeRequestDto(String name, String nextToken, Integer maxResults) {
        this.name = name;
        this.nextToken = nextToken;
        this.maxResults = maxResults;
    }
}
