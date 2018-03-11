package com.mytaxi.filterPattern;

import com.mytaxi.domainobject.BaseDO;

import java.util.List;

public interface Criteria<T extends BaseDO> {

    List<T> satisfy(List<T> collection);
}
