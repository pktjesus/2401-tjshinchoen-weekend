package com.test1.wishlist;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WishListDBEntity {
	private String title;			// 검색결과 제목
	private String category;		// 검색결과 카테고리
	private String jibunAddress;	// 검색결과 지번주소
	private String roadAddress;		// 검색결과 도로명주소
	private String homepageLink;	// 검색결과 홈페이지 주소
	private String imageLink;		// 이미지 검색결과 Link 주소
	private boolean isVisit;		// 방문여부
	private int visitCount;			// 방문횟수
	private LocalDateTime lastVisitDate; // 마지막 방문한 시간
}
