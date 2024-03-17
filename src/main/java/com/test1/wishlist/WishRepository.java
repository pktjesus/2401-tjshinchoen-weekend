package com.test1.wishlist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.test1.utils.StringUtils;

@Repository
public class WishRepository {
	private List<WishListDBEntity> wishDBEntityList 
		= new ArrayList<>();
	
	// 위시 리스트 저장
	public WishListDBEntity wishSave(WishListDTO wishListDto) {
		WishListDBEntity wishListDBEntity = new WishListDBEntity();
		wishListDBEntity.setTitle(StringUtils.removeTags(wishListDto.getTitle()));
		wishListDBEntity.setCategory(wishListDto.getCategory());
		wishListDBEntity.setJibunAddress(wishListDto.getJibunAddress());
		wishListDBEntity.setRoadAddress(wishListDto.getRoadAddress());
		wishListDBEntity.setHomepageLink(wishListDto.getHomepageLink());
		wishListDBEntity.setImageLink(wishListDto.getImageLink());
		wishListDBEntity.setVisit(true);
		wishListDBEntity.setVisitCount(1);
		wishListDBEntity.setLastVisitDate(LocalDateTime.now());
	
		wishDBEntityList.add(wishListDBEntity);
		
		return wishListDBEntity;
	}
	
	public List<WishListDBEntity> wishAll() {
		return wishDBEntityList;
	}
}
