package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.interfaces.FruitOperationService;

public class FruitBalanceServiceImpl implements FruitOperationService {
    private FruitDao fruitDao;

    public FruitBalanceServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(TransactionDto dto) {
        fruitDao.put(dto.getFruit(), dto.getQuantity());
    }
}

