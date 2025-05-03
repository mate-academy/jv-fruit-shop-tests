package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperatorStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        Storage.getFruits().clear();
    }

    @After
    public void cleanStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void processTransaction_withValidInformation_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 67);
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 37);
        FruitTransaction appleSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30);
        new BalanceHandler().operateFruits(appleBalance);
        new SupplyHandler().operateFruits(appleSupply);
        assertEquals(expected, Storage.getFruits());
    }

    @Test
    public void processFruit_transactionValid_Ok() {
        fruitShopService = new FruitShopServiceImpl(new OperatorStrategyImpl(operationHandlerMap));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        fruitShopService.processTransactions(list);
    }
}

