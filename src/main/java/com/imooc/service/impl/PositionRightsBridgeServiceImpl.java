package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.PositionRightsBridgeDao;
import com.imooc.entity.PositionRightsBridge;
import com.imooc.service.PositionRightsBridgeService;
import org.springframework.stereotype.Service;

@Service
public class PositionRightsBridgeServiceImpl extends ServiceImpl<PositionRightsBridgeDao, PositionRightsBridge> implements PositionRightsBridgeService {

    @Override
    public boolean append(PositionRightsBridge positionRightsBridge) {
        return baseMapper.insert(positionRightsBridge) != 0;
    }

    @Override
    public boolean removeRightsByPositionId(String positionId) {

        LambdaQueryWrapper<PositionRightsBridge> wrapper = new LambdaQueryWrapper();

        wrapper.eq(PositionRightsBridge::getPositionId, positionId);

        return baseMapper.delete(wrapper) != 0;
    }
}
