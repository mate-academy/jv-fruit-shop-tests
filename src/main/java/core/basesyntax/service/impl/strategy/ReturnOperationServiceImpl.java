package core.basesyntax.service.impl.strategy;

import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.exception.InvalidFruitDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;

public class ReturnOperationServiceImpl implements OperationService {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        if (fruitDao.getDataBaseContent().containsKey(fruitTransaction.getFruitName())) {
            fruitDao.addFruits(fruitTransaction.getFruitName(),
                    fruitTransaction.getQuantity());
        } else {
            throw new InvalidFruitDataException("There is no such fruit in the database: "
                    + fruitTransaction.getFruitName());
        }
    }
}
