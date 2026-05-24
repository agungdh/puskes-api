INSERT INTO users (uuid, username, password, email, name, role, created_at)
VALUES
    (gen_random_uuid(), 'admin', '$2a$10$obA2O6SBsQiPKQ215zAfZOqSIBJYyofpBw7sTe.tjDT742PYIAP3i', 'admin@puskes.local', 'Administrator', 'ADMIN', extract(epoch from now())::bigint),
    (gen_random_uuid(), 'petugas', '$2a$10$J9NKvJtmWqi9vV0nqy/XH.jPlUOXkDZz9afswd71/Os/x7pFRDZNO', 'petugas@puskes.local', 'Petugas', 'PETUGAS', extract(epoch from now())::bigint),
    (gen_random_uuid(), 'bumil', '$2a$10$Tyhifa6SgRtYafcFKCSf3eArt6v8Zofs7PlK97H6TVzlqfJnKG1D6', 'bumil@puskes.local', 'Bumil', 'BUMIL', extract(epoch from now())::bigint);
