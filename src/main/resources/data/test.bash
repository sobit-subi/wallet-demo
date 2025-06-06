test cases
bash
# create wallet
curl -X POST http://localhost:8080/api/wallet/create?userId=user001

# recharge 100
curl -X POST http://localhost:8080/api/wallet/recharge?userId=user001&amount=100

# comsume 30
curl -X POST http://localhost:8080/api/wallet/consume?userId=user001&amount=30

# check balance
curl http://localhost:8080/api/wallet/balance?userId=user001
# return:70.00

# check trasacton records
curl http://localhost:8080/api/wallet/transactions?userId=user001

# delete wallet
curl -X DELETE http://localhost:8080/api/wallet/delete?userId=user001
