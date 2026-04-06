CREATE TABLE IF NOT EXISTS quantity_measurement_entity (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    this_value DOUBLE NOT NULL,
    this_unit VARCHAR(50) NOT NULL,
    this_measurement_type VARCHAR(50) NOT NULL,

    that_value DOUBLE,
    that_unit VARCHAR(50),
    that_measurement_type VARCHAR(50),

    operation VARCHAR(20) NOT NULL,

    result_value DOUBLE,
    result_unit VARCHAR(50),
    result_measurement_type VARCHAR(50),

    result_string VARCHAR(255),

    is_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(500),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    entity_id BIGINT NOT NULL,

    operation_count INT DEFAULT 1,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (entity_id)
    REFERENCES quantity_measurement_entity(id)
);

CREATE TABLE IF NOT EXISTS app_users (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile_number VARCHAR(20) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
