package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class SupplyHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);

    @After
    public void after() {
        Storage.fruits.clear();
    }

    @Test
    public void handlerSupply_Ok() {
        String fruitName = "banana";
        fruitService.update(fruitName,134);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(23);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitService));

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationStrategy.getHandler(fruitTransaction.getOperation()).handle(fruitTransaction);
        int actualResult = Storage.fruits.get(fruitName);
        assertEquals(157, actualResult);
    }

}
