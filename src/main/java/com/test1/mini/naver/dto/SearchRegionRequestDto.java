package com.test1.naver.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchRegionRequestDto {
	private String query;
	private Integer display = 1;
	private Integer start = 1;
	private String sort = "random";
}
