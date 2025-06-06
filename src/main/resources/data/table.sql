### USER BALANCE TABLE
CREATE TABLE user_balance (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id VARCHAR(50) NOT NULL UNIQUE,
                              balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                              update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

### TRANSACTION RECORD TABLE
CREATE TABLE transaction_record (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id VARCHAR(50) NOT NULL,
                                    amount DECIMAL(15,2) NOT NULL,
                                    type ENUM('RECHARGE', 'CONSUME', 'REFUND') NOT NULL,
                                    description VARCHAR(255),
                                    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES user_balance(user_id)
);
