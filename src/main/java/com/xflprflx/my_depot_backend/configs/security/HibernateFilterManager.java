package com.xflprflx.my_depot_backend.configs.security;

import com.xflprflx.my_depot_backend.model.base.tenant.TenantContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class HibernateFilterManager {

    @PersistenceContext
    private EntityManager entityManager;

    public void enableFilters() {
        Session session = entityManager.unwrap(Session.class);

        // multi-tenant
        if (TenantContext.get() != null && session.getEnabledFilter("multiTenantFilter") == null) {
            session.enableFilter("multiTenantFilter")
                    .setParameter("tenantId", TenantContext.get());
        }

        // soft-delete
        if (session.getEnabledFilter("softDeleteFilter") == null) {
            session.enableFilter("softDeleteFilter")
                    .setParameter("isDeleted", false);
        }
    }
}
