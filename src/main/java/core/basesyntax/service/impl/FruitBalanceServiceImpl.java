package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.service.FruitBalanceService;

public class FruitBalanceServiceImpl implements FruitBalanceService {
    private FruitBalanceDao fruitBalanceDao;

    public FruitBalanceServiceImpl(FruitBalanceDao fruitBalanceDao) {
        this.fruitBalanceDao = fruitBalanceDao;
    }

    @Override
    public void updateFruitBalance(String fruit, int balance) {
        validateBalance(balance);
        if (fruitBalanceDao.get(fruit) == null) {
            fruitBalanceDao.add(new FruitBalance(fruit, balance));
        } else {
            fruitBalanceDao.get(fruit).setBalance(balance);
        }
    }

    private void validateBalance(int balance) {
        if (balance < 0) {
            throw new RuntimeException("Balance value can't be negative: " + balance);
        }
    }
}
