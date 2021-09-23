package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.Strategy;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.StrategyImpl;
import core.basesyntax.service.impl.SupplyOperation;
import core.basesyntax.service.impl.TransactionDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @Before
    public void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operationHandlerMap.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operationHandlerMap.put(Operation.RETURN.getOperation(), new ReturnOperation());
        operationHandlerMap.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(strategy);
    }

    @Test
    public void checkOperation_Ok() {
        List<TransactionDto> transactionData = new ArrayList<>();
        transactionData.add(new TransactionDto(Operation.BALANCE.getOperation(),
                new Fruit("apple"), 10));
        Map<Fruit, Integer> actual = new HashMap<>();
        actual.put(new Fruit("apple"), 10);
        Assert.assertEquals(actual,fruitService.saveDataToDb(transactionData));
        transactionData.add(new TransactionDto(Operation.PURCHASE.getOperation(),
                new Fruit("apple"), 2));
        actual.put(new Fruit("apple"), 8);
        Assert.assertEquals(actual,fruitService.saveDataToDb(transactionData));
        transactionData.add(new TransactionDto(Operation.RETURN.getOperation(),
                new Fruit("apple"), 3));
        actual.put(new Fruit("apple"), 11);
        Assert.assertEquals(actual,fruitService.saveDataToDb(transactionData));
        transactionData.add(new TransactionDto(Operation.SUPPLY.getOperation(),
                new Fruit("apple"), 3));
        actual.put(new Fruit("apple"), 14);
        Assert.assertEquals(actual,fruitService.saveDataToDb(transactionData));
    }

    @Test
    public void checkOperation_negativeAmount_notOk() {
        List<TransactionDto> transactionData = new ArrayList<>();
        transactionData.add(new TransactionDto(Operation.BALANCE.getOperation(),
                new Fruit("apple"), 1));
        transactionData.add(new TransactionDto(Operation.PURCHASE.getOperation(),
                new Fruit("apple"), 2));
        Assertions.assertThrows(RuntimeException.class, () ->
                fruitService.saveDataToDb(transactionData));
    }

    @Test
    public void checkOperation_nullOperation_notOk() {
        List<TransactionDto> transactionData = new ArrayList<>();
        transactionData.add(new TransactionDto(null,
                new Fruit("apple"), 1));
        Assertions.assertThrows(RuntimeException.class, () ->
                fruitService.saveDataToDb(transactionData));
    }
}
