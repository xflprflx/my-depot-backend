package com.xflprflx.my_depot_backend.model.base.tenant;

import com.xflprflx.my_depot_backend.exceptions.ResourceNotFoundException;
import com.xflprflx.my_depot_backend.exceptions.TenantMismatchException;
import com.xflprflx.my_depot_backend.model.base.SoftDeleteEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "multiTenantFilter", parameters = @ParamDef(name = "tenantId", type = Long.class))
@Filter(name = "multiTenantFilter", condition = "tenant_id = :tenantId")
public abstract class MultiTenantEntity extends SoftDeleteEntity {

    @Column(name = "tenant_id", nullable = false, updatable = false)
    private Long tenantId;

    @PrePersist
    protected void prePersist() {
        if (this.tenantId == null) {
            Long currentTenant = TenantContext.get();
            if (currentTenant == null) {
                throw new IllegalStateException("TenantContext não definido para persistência.");
            }
            this.tenantId = currentTenant;
        }
    }


    @PreUpdate
    protected void preUpdate() {
        if (this.isNotValidTenant()) {
            throw new TenantMismatchException("Operação não permitida para este tenant.");
        }
        if (super.isDeleted()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @PreRemove
    protected void preDelete() {
        if (tenantId != null && !tenantId.equals(TenantContext.get())) {
            throw new TenantMismatchException("Recurso não encontrado.");
        }
    }

    public Long getTenantId() {
        return tenantId;
    }

    private boolean isNotValidTenant() {
        return tenantId != null && !tenantId.equals(TenantContext.get());
    }
}
