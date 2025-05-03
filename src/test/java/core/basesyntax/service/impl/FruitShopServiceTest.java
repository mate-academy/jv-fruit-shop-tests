package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationStrategy;
import core.basesyntax.strategy.impl.FactoryStrategy;
import core.basesyntax.strategy.impl.PurchaseOperationStrategy;
import core.basesyntax.strategy.impl.ReturnOperationStrategy;
import core.basesyntax.strategy.impl.SupplyOperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceTest {
    private static FruitDao fruitDao;
    private static FruitShopService fruitShopService;
    private static final String FRUIT_TEST = "banana";
    private static final int AMOUNT_TEST = 13;
    private static final FruitTransaction.Operation OPERATION_TEST
            = FruitTransaction.Operation.SUPPLY;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationStrategy> strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationStrategy(fruitDao));
        strategies.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationStrategy(fruitDao));
        strategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationStrategy(fruitDao));
        strategies.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationStrategy(fruitDao));
        fruitShopService = new FruitShopService(new FactoryStrategy(strategies));
    }

    @Test
    public void processing_Ok() {
        List<FruitTransaction> listOperations = new ArrayList<>();
        listOperations.add(new FruitTransaction(OPERATION_TEST, FRUIT_TEST, AMOUNT_TEST));
        fruitShopService.processing(listOperations);
        assertEquals(AMOUNT_TEST, fruitDao.get(FRUIT_TEST));
    }

    @After
    public void tearDown() {
        fruitDao.clear();
    }
}
