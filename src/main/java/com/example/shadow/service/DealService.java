package com.example.shadow.service;

import com.example.shadow.entity.Deal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shadow.entity.VO.*;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-11-01
 */
public interface DealService extends IService<Deal> {
    void commitDeal(Integer uid, DealCreateVO dealCreateVO);

    //List<DealVO> listDeals(Integer uid, String filter, Integer pageNo, Integer pageSize);
    List<DealVO> listDeals(Integer uid, String serviceName,Integer pageNo, Integer pageSize);

    List<DealmyVO> listMyDeals(Integer uid, Integer pageNo, Integer pageSize);

    DealDetailVO getDetail(Integer dealId);

    DealServiceVO getDealService(Integer dealId);

    void approveDeal(Integer dealId);

    void rejectDeal(Integer dealId);

    void assignDeal(Integer dealId);

    List<DealVO> listUnassignedDeals(Integer pageNo, Integer pageSize);

    void rejectAssignDeal(Integer dealId);

    List<DealVO> listFeeList(Integer pageNo, Integer pageSize);
}
