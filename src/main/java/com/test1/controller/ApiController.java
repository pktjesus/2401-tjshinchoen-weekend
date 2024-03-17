package com.test1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test1.wishlist.WishListDBEntity;
import com.test1.wishlist.WishListDTO;
import com.test1.wishlist.WishListService;

@RestController		// response를 json바꿔주는 controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private WishListService wishListService;
	
	// 1. 검색 API(GET)
	@GetMapping("/search")
	public WishListDTO search(@RequestParam String searchQuery) {
		WishListDTO wishListDto = wishListService.search(searchQuery);
		System.out.println("wishListDto: " + wishListDto);
		
		return wishListDto;
	}
	
	// 2. 위시리스트 추가 API(POST)
	@PostMapping("/wishadd")
	public WishListDBEntity wishAdd(
			@RequestBody WishListDTO wishListDto) {
		return wishListService.addWish(wishListDto);
	}
	
	// 3. 위시리스트 목록 가져오기
	@GetMapping("/wishall")
	public List<WishListDBEntity> wishAll() {
		return wishListService.allWish();
	}
	
	// 4. 방문 추가
	
	// 5. 위시리스트 삭제
	
}
