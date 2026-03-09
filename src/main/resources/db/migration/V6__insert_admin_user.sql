INSERT INTO users (email, password, first_name, last_name, middle_name, passport_url)
VALUES ('admin@gogo.com', '$2a$10$Nmh0JBkmzCYJLQV/8GiV/.QZd.r6O2DCOZD3WHwDjKnrmMFUzl/u2', 'Admin', 'User', 'GoGo', 'ADMIN')
    ON CONFLICT(email) DO NOTHING;

INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_ADMIN' FROM users WHERE email = 'admin@gogo.com'
    ON CONFLICT DO NOTHING;