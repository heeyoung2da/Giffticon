package com.ncncn.mapper;

import com.ncncn.domain.DealDetailVO;
import com.ncncn.domain.GifticonVO;
import com.ncncn.domain.ProdListVO;
import com.ncncn.domain.pagination.SaleGftCriteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j
public class GifticonMapperTests {

    @Setter(onMethod_ = @Autowired)
    GifticonMapper mapper;

    @Test
    public void gftDealCmplTest(){

        int gftId = 9999;

        int updateCmpl = mapper.gftDealCmpl(gftId);

        assertEquals(updateCmpl,0);
    }

//    @Test
//    public void deleteGifticonTest(){
//
//        int gftId = 56;
//
//        int deleteCmpl = mapper.deleteGifticon(gftId);
//
//        assertEquals(deleteCmpl, 1);
//    }

    @Test
    public void updateGftPrcTests(){

        int gftId = 152;
        char isAutoPrc = '1';
        int dcPrc = 5000;
        double dcRate = 0.20;
        int updatePrcCompl = mapper.updateGftPrc(gftId, isAutoPrc, dcPrc, dcRate);

        assertEquals(updatePrcCompl, 1);

    }

    @Test
    public void updateGftPrcTests2(){

        int gftId = 53;
        char isAutoPrfc = '0';
        int dcPrc = 10000;
        double dcRate = 0.10;
        int updatePrcCompl = mapper.updateGftPrc(gftId,isAutoPrfc,dcPrc,dcRate);

        assertEquals(updatePrcCompl, 1);

    }

    @Test
    public void countStus002Test(){

        String prodCode = "010101";

        int count = mapper.countOnselling(prodCode);

        assertEquals(count,3);
    }

    @Test
    public void getMainGftByBrdNameTest(){

        String brdName = "스타벅스";

        List<ProdListVO> mainGftList = mapper.getMainGftByBrandName(brdName);

        assertEquals(mainGftList.size(), 25);
    }

    @Test
    public void readTest(){
        int id = 102;

        ProdListVO gifti = mapper.getGifticon(id);

    }

    @Test
    public void updateGftStus(){
        int id = 7;

        Boolean result = mapper.updateGftStus(id);

        assertEquals(result, true);

    }

    @Test
    public void getNotCmplGft(){

        List<DealDetailVO> notCmplList = mapper.getNotCmplGft();

        assertEquals(notCmplList.size(), 2);
    }

    @Test
    public void autoDealCmpl(){

        List<DealDetailVO> gftList = mapper.getNotCmplGft();

        if(gftList.size() != 0){

            int isUpdated = mapper.autoDealCmpl(gftList);

        }else{
            log.info("상태변경할 기프티콘이 없습니다.");
        }

    }

}
