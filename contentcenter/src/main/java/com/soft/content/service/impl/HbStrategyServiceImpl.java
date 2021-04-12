package com.soft.content.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.RuleDto;
import com.soft.content.model.dto.SecKillAddDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.model.entity.HbStrategy;
import com.soft.content.repository.HbGoodRepository;
import com.soft.content.repository.HbStrategyRepository;
import com.soft.content.service.HbStrategyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbStrategyServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:49:00
 */
@Service
@Transactional
public class HbStrategyServiceImpl implements HbStrategyService {
    @Resource
    private HbStrategyRepository hbStrategyRepository;

    @Resource
    private HbGoodRepository hbGoodRepository;

    @Override
    public List<HbStrategy> findAllStrategy() {
        return hbStrategyRepository.findAll();
    }

    @Override
    public ResponseResult addStrategy(SecKillAddDto secKillAddDto) throws ParseException {
        System.out.println(secKillAddDto);
        System.out.println(secKillAddDto);
        /**
         * 1.把秒杀时刻更新为创建时间
         * 2.查询该商品的所有活动规则，直接删除
         * 3.创建新的活动规则
         * 4.把活动描述更新为商品描述
         */
        //  1.查询该商品的所有活动规则，直接删除
        hbStrategyRepository.deleteHbStrategiesByGoodIdEquals(secKillAddDto.getGoodId());
        //  2. 拼凑时间
        String startTime = (secKillAddDto.getDay().getYear() + 1900) + "-" + (secKillAddDto.getDay().getMonth() + 1) + "-" + secKillAddDto.getDay().getDate() + " " + secKillAddDto.getDetail().getHours() + ":" + secKillAddDto.getDetail().getMinutes() + ":" + secKillAddDto.getDetail().getSeconds();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = sdf.parse(startTime);

        /**
         {
         "goodId": "2",
         "day": "2021-04-13 16:51",
         "detail": "2021-04-11 12:51",
         "description": "demoData",
         "ruleDtoList": [{
         "start": 1,
         "end": 3,
         "discount": 1
         }]
         }
         */
        //  3.生成新的活动规则
        List<RuleDto> ruleDtoList = secKillAddDto.getRuleDtoList();
        for (RuleDto ruleDto : ruleDtoList) {
            HbStrategy hbStrategy = new HbStrategy();
            hbStrategy.setPkStrategyId(UUID.randomUUID().toString().substring(0, 19));
            hbStrategy.setGoodId(secKillAddDto.getGoodId());
            hbStrategy.setStrategyName(secKillAddDto.getDescription());
            hbStrategy.setCreatedTime(Timestamp.valueOf(startTime));
            hbStrategy.setUpdatedTime(Timestamp.valueOf(startTime));
            hbStrategy.setRankStart(ruleDto.getStart());
            hbStrategy.setRankEnd(ruleDto.getEnd());
            //此处 7折变为0.7
            System.out.println(Double.parseDouble(String.valueOf(ruleDto.getDiscount())) / 10);
            hbStrategy.setDiscount(Double.parseDouble(String.valueOf(ruleDto.getDiscount())) / 10);
            hbStrategyRepository.save(hbStrategy);
        }

        //  4.修改商品的描述
        HbGood hbGood = hbGoodRepository.getOne(secKillAddDto.getGoodId());
        hbGood.setDescription(secKillAddDto.getDescription());
        hbGoodRepository.save(hbGood);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult getStrategy(String goodId) {
        SecKillAddDto secKillAddDto = new SecKillAddDto();
//        private String goodId;
//        private Date day;
//        private Date detail;
//        private String description;
//        private List<RuleDto> ruleDtoList;
        List<HbStrategy> list = hbStrategyRepository.findHbStrategiesByGoodIdEquals(goodId);
        secKillAddDto.setGoodId(goodId);
        secKillAddDto.setDay(list.get(0).getCreatedTime());
        secKillAddDto.setDetail(list.get(0).getCreatedTime());
        secKillAddDto.setDescription(list.get(0).getStrategyName());
        List<RuleDto> ruleDtos = new ArrayList<>();
        for (HbStrategy hbStrategy : list) {
            RuleDto ruleDto = new RuleDto();
            ruleDto.setStart(hbStrategy.getRankStart());
            ruleDto.setEnd(hbStrategy.getRankEnd());
//            不进行四舍五入操作：
//            (int)x
//            进行四舍五入操作：
//            Integer.parseInt(new java.text.DecimalFormat("0").format(x))
            ruleDto.setDiscount((int) (hbStrategy.getDiscount() * 10));
            ruleDtos.add(ruleDto);
        }
        secKillAddDto.setRuleDtoList(ruleDtos);
        return ResponseResult.success(secKillAddDto);
    }
}
