package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.PositionRightsBridge;

public interface PositionRightsBridgeService extends IService<PositionRightsBridge> {

    /**
     * 添加
     *
     * @param positionRightsBridge
     * @return
     */
    boolean append(PositionRightsBridge positionRightsBridge);

    /**
     * 根据职位 id 删除其所有的权限
     *
     * @param positionId
     * @return
     */
    boolean removeRightsByPositionId(String positionId);
}
