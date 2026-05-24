CREATE TABLE kehamilan (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL DEFAULT gen_random_uuid(),
    user_id BIGINT NOT NULL REFERENCES users(id),
    tgl_haid_terakhir DATE,
    tgl_perkiraan_lahir DATE,
    nama_bayi VARCHAR(255),
    jenis_kelamin VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'HAMIL',
    created_at BIGINT,
    updated_at BIGINT,
    deleted_at BIGINT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);

CREATE INDEX idx_kehamilan_uuid ON kehamilan USING hash (uuid);
CREATE INDEX idx_kehamilan_user ON kehamilan (user_id);
