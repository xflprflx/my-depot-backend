package com.xflprflx.my_depot_backend.model;

import com.xflprflx.my_depot_backend.model.base.tenant.MultiTenantEntity;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "branches",
        uniqueConstraints = @UniqueConstraint(
                name = "branches_name_tenant_id_ukey",
                columnNames = {"name", "tenant_id"}
        )
)
public class Branch extends MultiTenantEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "branches_company_fkey"))
    private Company company;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(id, branch.id) && Objects.equals(name, branch.name) && Objects.equals(company, branch.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company);
    }
}
