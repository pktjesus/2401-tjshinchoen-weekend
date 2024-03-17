package com.test1.wishlist;

import lombok.Data;

@Data
public class WishListDTO {
	private String title;			// 검색결과 제목
	private String category;		// 검색결과 카테고리
	private String jibunAddress;	// 검색결과 지번주소
	private String roadAddress;		// 검색결과 도로명주소
	private String homepageLink;	// 검색결과 홈페이지 주소
	private String imageLink;		// 이미지 검색결과 Link 주소
}
