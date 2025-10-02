INSERT INTO resources (name, description) VALUES ('CATEGORIES', 'Gerenciamento de categorias');
INSERT INTO resources (name, description) VALUES ('PRODUCTS', 'Gerenciamento de produtos');
INSERT INTO resources (name, description) VALUES ('PRODUCT_INSTANCES', 'Gerenciamento de produtos');

INSERT INTO permissions (resource_id, action) VALUES (1, 'READ');
INSERT INTO permissions (resource_id, action) VALUES (1, 'WRITE');
INSERT INTO permissions (resource_id, action) VALUES (2, 'READ');
INSERT INTO permissions (resource_id, action) VALUES (2, 'WRITE');
INSERT INTO permissions (resource_id, action) VALUES (3, 'READ');
INSERT INTO permissions (resource_id, action) VALUES (3, 'WRITE');

INSERT INTO access_profiles (name, tenant_id, deleted, deleted_at) VALUES ('OPERATOR', 1, false, null);
INSERT INTO access_profiles (name, tenant_id, deleted, deleted_at) VALUES ('ADMIN', 1, false, null);
INSERT INTO access_profiles (name, tenant_id, deleted, deleted_at) VALUES ('OPERATOR', 2, false, null);
INSERT INTO access_profiles (name, tenant_id, deleted, deleted_at) VALUES ('ADMIN', 2, false, null);

INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(1, 1);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(1, 3);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 1);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 2);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 3);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 4);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 5);
INSERT INTO access_profiles_permissions (access_profile_id, permission_id) VALUES(2, 6);

INSERT INTO companies (name, deleted, deleted_at, tenant_id) VALUES ('Meta', false, null, 1);
INSERT INTO companies (name, deleted, deleted_at, tenant_id) VALUES ('Google', false, null, 2);

INSERT INTO branches (name, company_id, tenant_id, deleted, deleted_at) VALUES ('POA-01', 1, 1, false, null);
INSERT INTO branches (name, company_id, tenant_id, deleted, deleted_at) VALUES ('JOI-01', 2, 2, false, null);

INSERT INTO users (name, email, password, access_profile_id, branch_id, account_non_expired, account_non_locked, credentials_non_expired, enabled, tenant_id, deleted, deleted_at) VALUES ('Filipe', 'x.filipe.machado.x@gmail.com', '$2a$10$aIqzBdIeH71GWvzQNKIMBetbQ6s09g/MNDnWLVNBOpNEeeHWDiW0C', 2, 1, true, true, true, true, 1, false, null);
INSERT INTO users (name, email, password, access_profile_id, branch_id, account_non_expired, account_non_locked, credentials_non_expired, enabled, tenant_id, deleted, deleted_at) VALUES ('Josnel', 'x.josnel.machado.x@gmail.com', '$2a$10$aIqzBdIeH71GWvzQNKIMBetbQ6s09g/MNDnWLVNBOpNEeeHWDiW0C', 1, 2, true, true, true, true, 2, false, null);