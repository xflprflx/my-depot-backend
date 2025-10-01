package com.xflprflx.my_depot_backend.model.base.tenant;

public final class TenantContext {
    private static final ThreadLocal<Long> TENANT = new ThreadLocal<>();

    public static void set(Long tenantId) { TENANT.set(tenantId); }
    public static Long get() { return TENANT.get(); }
    public static void clear() { TENANT.remove(); }
}
