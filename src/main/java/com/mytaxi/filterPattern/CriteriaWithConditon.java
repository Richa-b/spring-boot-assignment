package com.mytaxi.filterPattern;

import com.mytaxi.domainobject.BaseDO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CriteriaWithConditon<T extends BaseDO> implements Criteria<T> {

    private Predicate<T> filterCondition;

    public CriteriaWithConditon(Predicate<T> predicate) {
        this.filterCondition = predicate;
    }

    @Override
    public List<T> satisfy(List<T> collection) {
        return collection.stream()
                .filter(filterCondition)
                .collect(Collectors.toList());
    }
}
