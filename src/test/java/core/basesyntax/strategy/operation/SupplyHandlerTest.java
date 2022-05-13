package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class SupplyHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void handleSupply_Ok() {
        String fruitName = "banana";
        fruitDao.update(fruitName, 1);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setQuantity(15);
        transaction.setFruit(fruitName);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitDao));

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationStrategy.getHandler(transaction.getOperation()).handle(transaction);
        int actual = fruitDao.getQuantity(fruitName);
        assertEquals(16, actual);
    }
}
