package com.mytaxi.filterPattern;

import com.mytaxi.domainobject.BaseDO;

import java.util.List;

public class AndCriteria<T extends BaseDO> implements Criteria<T> {

    private Criteria[] criterias;

    public AndCriteria(Criteria... criterias){
       this.criterias = criterias;
    }

    @Override
    public List<T> satisfy(List<T> collection) {
        List<T> filteredCollection = collection;
        for (Criteria<T> criteria : criterias) {
            filteredCollection = criteria.satisfy(filteredCollection);
        }
        return filteredCollection;
    }
}
