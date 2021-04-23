package com.ncncn.service;

import com.ncncn.domain.pagination.MyPageCriteria;
import com.ncncn.domain.WishListVO;

import java.util.List;

public interface WishListService {

	// 관심상품에 추가하기
	public int insertWish(WishListVO wish);

	// 관심상품에서 삭제하기
	public int deleteWish(WishListVO wish);

	// 관심상품이 있는지 조회
	public int hasWish(WishListVO wish);

	// 유저 - 관심상품 조회
	public List<WishListVO> getWishList(int userId);

	// 관심상품 조회(페이징 처리)
	public List<WishListVO> getWishListWithPaging(int userId, MyPageCriteria cri);

	public int getTotalCount(int userId);

}
