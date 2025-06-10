wallet api is a financial prodcut for test
api has 3 transaction mode : [RECHARGE,CONSUME,REFUND]
in this test only implemented RECHARGER and CONSUME transactions
### api description
by using user api generate user identity and token.
token will be used in wallet api for security concerns
1. ** user register/login **
    - [register user]:POST /api/user/register
    - [login user]   :POST /api/user/login 
2. **user balance management**：
    - [create wallet]:POST /api/wallet/create?userId=123
    - [recharge]     :POST /api/wallet/recharge?userId=123&amount=100.00
    - [consume]      :POST /api/wallet/consume?userId=123&amount=50.00
    - [check balance]:GET /api/wallet/balance?userId=123
    - [delete wallet]:DELETE /api/wallet/delete?userId=123

3. **user transaction records**：
    - [check transaction] :GET /api/wallet/transactions?userId=123

design api key points
1. ** Transaction Management **:
   use @Transactional to ensure atomicity of balance updates and transaction record insertions
2. ** balance accuracy **
   use BigDecimal to proccess amounts to avoid floaating-point number accuracy issues
3. ** Transaction Type **
   use ENUM type to distinguish between recharge/consume/refund
5. ** Balance Update **
   in mapper xml, directly execute SQL update operations to avoid concurrency issues
6. ** AuthAccess annotation **
   use annotation ir order to multi use and code readability
7. ** Design Exception handler **

FOR TEST MOCK url and data
POST /api/user/register
// User 1
{
"userId": "akmal_003",
"password": "pass123",
"name": "Akmal Daminov",
"phone": "13800010001",
"email": "user1@test.com"
}

// User 2
{
"userId": "bobur_001",
"password": "pass456",
"name": "Bobur Bozorov",
"phone": "13800020002",
"email": "user2@test.com"
}

// User 3
{
"userId": "toxir_003",
"password": "pass789",
"name": "Toxir Karimov",
"phone": "13800030003",
"email": "user3@test.com"
}

// User 4
{
"userId": "Omon_007",
"password": "passabc",
"name": "Omon Rustamov",
"phone": "13800040004",
"email": "user4@test.com"
}

// User 5
{
"userId": "alisher_008",
"password": "passxyz",
"name": "username005",
"phone": "13800050005",
"email": "user5@test.com"
}

# User 1
URL: /api/wallet/create?userId=user_001

# User 2
URL: /api/wallet/create?userId=user_002

# User 3
URL: /api/wallet/create?userId=user_003

# User 4
URL: /api/wallet/create?userId=user_004

# User 5
URL: /api/wallet/create?userId=user_005

### 1. recharge
POST /api/wallet/recharge?userId=user_001&amount=500.00&remark=salary

### 2. recharge
POST /api/wallet/recharge?userId=user_001&amount=800.00&remark=bonus

### 3. recharge
POST /api/wallet/recharge?userId=user_001&amount=1200.00

### 4. consume
POST /api/wallet/consume?userId=user_001&amount=300.00&remark=buy iPhone 16 PM

### 5. recharge
POST /api/wallet/recharge?userId=user_001&amount=200.00&remark=return loan

### 6. consume
POST /api/wallet/consume?userId=user_001&amount=150.00&remark=buy groceries

### 7. consume
POST /api/wallet/consume?userId=user_001&amount=450.00&remark=repair PC

### 8. recharge
POST /api/wallet/recharge?userId=user_001&amount=1000.00

### 9. consume
POST /api/wallet/consume?userId=user_001&amount=200.00&remark=invite lady for a dinner

### 10. recharge
POST /api/wallet/recharge?userId=user_001&amount=300.00&remark=part time income

### 11. consume
POST /api/wallet/consume?userId=user_001&amount=100.00

### 12. consume
POST /api/wallet/consume?userId=user_001&amount=600.00&remark=buy antiradar

### 13. recharge
POST /api/wallet/recharge?userId=user_001&amount=400.00

### 14. consume
POST /api/wallet/consume?userId=user_001&amount=250.00&remark=summer clothes and socks

### 15. recharge
POST /api/wallet/recharge?userId=user_001&amount=700.00&remark=project award