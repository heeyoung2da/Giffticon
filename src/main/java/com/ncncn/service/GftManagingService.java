package com.ncncn.service;

import java.util.List;
import java.util.Map;

import com.ncncn.domain.DealDetailVO;
import com.ncncn.domain.GifticonVO;
import com.ncncn.domain.pagination.SaleGftCriteria;
import com.ncncn.domain.pagination.SaleRqustCriteria;
import com.ncncn.domain.SaleRqustVO;

public interface GftManagingService {

	List<SaleRqustVO> getAllRqust(SaleRqustCriteria cri);

	Map<String, Object> getRqustById(int id);

	int getTotalCount(SaleRqustCriteria cri);

	void approveRequest(int id, Map<String, String> rqust);

	int removeRqust(int id);

	List<Map<String, Object>> getAllSaleGifticon(SaleGftCriteria cri);

	Map<String, Object> getSaleGifticon(int gftId);

	int getTotalSaleGftCount(SaleGftCriteria cri);

	int modifyGftStus(int id, String stus);

	public List<DealDetailVO> getNotCmplGifticon();

	public int autoDealCmpl(List<DealDetailVO> gftList);
}
