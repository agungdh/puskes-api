CREATE TABLE pertumbuhan (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL DEFAULT gen_random_uuid(),
    kehamilan_id BIGINT NOT NULL REFERENCES kehamilan(id),
    berat_badan DOUBLE PRECISION NOT NULL,
    tinggi_badan DOUBLE PRECISION NOT NULL,
    imt DOUBLE PRECISION NOT NULL,
    tgl_pengukuran DATE NOT NULL,
    keterangan VARCHAR(500),
    created_at BIGINT,
    updated_at BIGINT,
    deleted_at BIGINT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_by VARCHAR(255)
);

CREATE INDEX idx_pertumbuhan_uuid ON pertumbuhan USING hash (uuid);
CREATE INDEX idx_pertumbuhan_kehamilan ON pertumbuhan (kehamilan_id);
