package com.test1.wishlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test1.naver.NaverAPIClient;
import com.test1.naver.dto.SearchImageRequestDto;
import com.test1.naver.dto.SearchImageResponseDto;
import com.test1.naver.dto.SearchImageResponseDto.SearchImageItem;
import com.test1.naver.dto.SearchRegionRequestDto;
import com.test1.naver.dto.SearchRegionResponseDto;
import com.test1.naver.dto.SearchRegionResponseDto.SearchRegionItem;

@Service
public class WishListService {
	@Autowired
	private NaverAPIClient naverAPIClient;
	
	@Autowired
	private WishRepository wishRepository;
	
	public WishListDTO search(String paramQuery) {
		WishListDTO wishListDto = new WishListDTO();
		
		// 1. NaverAPI 지역검색 호출해서 dto 값 매핑
		SearchRegionRequestDto searchRegionRequestDto = 
				SearchRegionRequestDto.builder()
					.query(paramQuery)
					.build();
		
		SearchRegionResponseDto searchRegionResponseDto 
			= naverAPIClient.searchRegion(searchRegionRequestDto);
		List<SearchRegionItem> listSearchRegionItem = searchRegionResponseDto.getItems();
		if(listSearchRegionItem != null && listSearchRegionItem.size() > 0) {
			SearchRegionItem searchRegionItem = listSearchRegionItem.get(0);
			
			wishListDto.setTitle(searchRegionItem.getTitle());	
			wishListDto.setCategory(searchRegionItem.getCategory());
			wishListDto.setJibunAddress(searchRegionItem.getAddress());
			wishListDto.setRoadAddress(searchRegionItem.getRoadAddress());
			wishListDto.setHomepageLink(searchRegionItem.getLink());
		}
		
		// 2. NaverAPI 이미지검색 호출해서 dto 값 매핑
		SearchImageRequestDto searchImageRequestDto = 
				SearchImageRequestDto.builder()
					.query(paramQuery)
					.build();
		
		SearchImageResponseDto searchImageResponseDto 
			= naverAPIClient.searchImage(searchImageRequestDto);
		List<SearchImageItem> listSearchImageItem = searchImageResponseDto.getItems();
		if(listSearchImageItem != null && listSearchImageItem.size() > 0) {
			SearchImageItem searchImageItem = listSearchImageItem.get(0);
			
			wishListDto.setImageLink(searchImageItem.getLink());
		}
		
		return wishListDto;
	}
	
	// 위시리스트 추가
	public WishListDBEntity addWish(WishListDTO wishListDto) {
		WishListDBEntity wishDBEntity = wishRepository.wishSave(wishListDto);
		return wishDBEntity;
	}
	
	// 위시리스트 목록 가져오기
	public List<WishListDBEntity> allWish() {
		List<WishListDBEntity> wishDBEntityList = wishRepository.wishAll();
		return wishDBEntityList;
	}
	
	
}
