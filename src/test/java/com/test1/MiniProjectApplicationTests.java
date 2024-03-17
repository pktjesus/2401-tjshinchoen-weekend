package com.test1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test1.naver.NaverAPIClient;
import com.test1.naver.dto.SearchImageRequestDto;
import com.test1.naver.dto.SearchImageResponseDto;
import com.test1.naver.dto.SearchRegionRequestDto;
import com.test1.naver.dto.SearchRegionResponseDto;

@SpringBootTest
class MiniProjectApplicationTests {

	@Autowired
	private NaverAPIClient naverAPIClient;
	
	@Test
	public void naverSearchRegionAPITest() {
		String paramQuery = "아구찜";
		
		// 지역검색 API Call method 테스트
		SearchRegionRequestDto searchRegionRequestDto = 
				SearchRegionRequestDto.builder()
					.query(paramQuery)
					.build();
		
		SearchRegionResponseDto searchRegionResponseDto 
			= naverAPIClient.searchRegion(searchRegionRequestDto);
		System.out.println("local 검색 response json: " + searchRegionResponseDto);
	}
	
	@Test
	public void naverSearchImageAPITest() {
		String paramQuery = "아구찜";
		
		// 이미지 API Call method 테스트
		SearchImageRequestDto searchImageRequestDto = 
				SearchImageRequestDto.builder()
					.query(paramQuery)
					.build();
		
		SearchImageResponseDto searchImageResponseDto 
			= naverAPIClient.searchImage(searchImageRequestDto);
		System.out.println("이미지 검색 response json: " + searchImageResponseDto);
	}
}
