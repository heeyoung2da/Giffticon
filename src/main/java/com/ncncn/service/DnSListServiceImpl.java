package com.ncncn.service;

import com.ncncn.domain.MyDealsDTO;
import com.ncncn.mapper.DnSListMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
@AllArgsConstructor
public class DnSListServiceImpl implements DnSListService{

    private DnSListMapper mapper;

    @Override
    public List<MyDealsDTO> getDeals(String email){
        log.info("DnS Service Impl......");

        return mapper.getDeals(email);
    }
}
