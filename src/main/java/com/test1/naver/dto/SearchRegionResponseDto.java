package com.test1.naver.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRegionResponseDto {
	private String lastBuildDate;
	private Integer total;
	private Integer start;
	private Integer display;
	private List<SearchRegionItem> items;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SearchRegionItem {
		private String title;
		private String link;
		private String category;
		private String description;
		private String telephone;
		private String address;
		private String roadAddress;
		private String mapx;
		private String mapy;
	}
}
