package com.xflprflx.my_depot_backend.model.access_control;

import com.xflprflx.my_depot_backend.model.enums.Action;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permissions")
public class Permission implements Serializable, GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @Enumerated(EnumType.STRING)
    private Action action;

    public Permission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && Objects.equals(resource, that.resource) && action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resource, action);
    }

    @Override
    public String getAuthority() {
        return this.resource.getName().toUpperCase() + "_" + this.action.name().toUpperCase();
    }
}
