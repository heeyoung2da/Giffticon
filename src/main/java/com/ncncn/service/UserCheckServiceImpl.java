package com.ncncn.service;

import com.ncncn.domain.CriteriaCH;
import com.ncncn.domain.UserVO;
import com.ncncn.mapper.UserCheckMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j
@Service
@AllArgsConstructor
public class UserCheckServiceImpl implements UserCheckService {

    @Setter(onMethod_ = @Autowired)
    private UserCheckMapper mapper;

//    @Override
//    public List<UserVO> getUserList(){
//        log.info("getUserList...........");
//        return mapper.getUserList();
//    }

    // 299page
    @Override
    public List<UserVO> getUserList(CriteriaCH cri) {

        log.info("get List with Criteria: " + cri);

        return mapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(CriteriaCH cri) {

        log.info("get total count");
        return mapper.getTotalCount(cri);
    }

}