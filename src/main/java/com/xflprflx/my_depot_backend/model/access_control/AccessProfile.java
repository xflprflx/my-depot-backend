package com.xflprflx.my_depot_backend.model.access_control;

import com.xflprflx.my_depot_backend.model.base.tenant.MultiTenantEntity;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "access_profiles",
        uniqueConstraints = @UniqueConstraint(
                name = "access_profiles_name_tenant_id_ukey",
                columnNames = {"name", "tenant_id"}
        )
    )
public class AccessProfile extends MultiTenantEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "access_profiles_permissions",
            joinColumns = @JoinColumn(name = "access_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();

    public AccessProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccessProfile accessProfile = (AccessProfile) o;
        return Objects.equals(id, accessProfile.id) && Objects.equals(name, accessProfile.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
