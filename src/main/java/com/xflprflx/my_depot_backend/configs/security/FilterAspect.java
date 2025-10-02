package com.xflprflx.my_depot_backend.configs.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterAspect {

    private final HibernateFilterManager hibernateFilterManager;

    public FilterAspect(HibernateFilterManager hibernateFilterManager) {
        this.hibernateFilterManager = hibernateFilterManager;
    }

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void enableHibernateFilters() {
        hibernateFilterManager.enableFilters();
    }
}
