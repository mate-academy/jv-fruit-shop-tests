package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class BalanceOperationHandler implements OperationHandler {
    private final FruitDao dao = new FruitDaoImpl();

    @Override
    public void handle(FruitTransaction transaction) {
        List<FruitTransaction> balance
                = dao.getByOperation(FruitTransaction.Operation.BALANCE);
        for (FruitTransaction balanceFruit : balance) {
            if (balanceFruit.getFruit().equals(transaction.getFruit())) {
                int currentTransactionFruitQuantity = transaction.getQuantity();
                balanceFruit.setQuantity(currentTransactionFruitQuantity);
            }
        }
    }
}