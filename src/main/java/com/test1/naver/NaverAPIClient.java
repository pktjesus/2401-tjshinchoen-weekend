package com.test1.naver;

import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.test1.naver.dto.SearchImageRequestDto;
import com.test1.naver.dto.SearchImageResponseDto;
import com.test1.naver.dto.SearchRegionRequestDto;
import com.test1.naver.dto.SearchRegionResponseDto;

import java.net.URI;

@Component
public class NaverAPIClient {

	// 1. 지역검색 API Call method
	public SearchRegionResponseDto searchRegion(SearchRegionRequestDto searchRegionRequestDto) {
		// API Call Library -> RestTemplate(Sync), WebClient(Async), Retry(Async), HttpClient(Sync) etc.....
		// (1) Header설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", "각자아이디");
		headers.set("X-Naver-Client-Secret", "각자암호");
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// (2) Request 설정
		URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")	// 호출할 서버 domain주소
						.path("/v1/search/local.json")
						.queryParam("query", searchRegionRequestDto.getQuery())
						//.queryParam("display", searchRegionRequestDto.getDisplay())
						.encode()
						.build()
						.toUri()
						;
		
		// (3) Response 설정
		// (4) 실제 API Call
		HttpEntity httpEntity = new HttpEntity(headers);
		ResponseEntity<SearchRegionResponseDto> responseRestTemplate
			= new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, SearchRegionResponseDto.class);
		
		return responseRestTemplate.getBody();
	}
	
	// 2. 이미지검색 API Call method
	public SearchImageResponseDto searchImage(SearchImageRequestDto searchImageRequestDto) {
		// (1) Header설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", "각자아이디");
		headers.set("X-Naver-Client-Secret", "각자암호");
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// (2) Request 설정
		String searchQuery = searchImageRequestDto.getQuery();
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")     // 호출할 서버 domain 주소
                .path("/v1/search/image")             // 호출할 서버 path
                .queryParam("query", searchQuery)     // 호출할 query string
                .encode()                                   // 한글처리
                .build()
                .toUri()
                ;
		
		// (3) Response 설정
		// (4) 실제 API Call
		HttpEntity httpEntity = new HttpEntity(headers);
		ResponseEntity<SearchImageResponseDto> responseRestTemplate = new RestTemplate()
                .exchange(uri, HttpMethod.GET, httpEntity, SearchImageResponseDto.class);
		
		return responseRestTemplate.getBody();
	}
	
}
