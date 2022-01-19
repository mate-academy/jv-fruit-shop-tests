package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.model.TransactionType;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class TransactionServiceImplTest {
    private static final Transaction balanceTransaction = new Transaction(TransactionType.BALANCE,
            "fruit", 100);
    private static final Transaction purchaseTransaction = new Transaction(TransactionType.PURCHASE,
            "fruit", 53);
    private static final Transaction returnTransaction = new Transaction(TransactionType.RETURN,
            "fruit", 31);
    private static final Transaction supplyTransaction = new Transaction(TransactionType.SUPPLY,
            "fruit", 7);
    private static Map<Fruit, Integer> fruitMap;
    private static FruitDao fruitDao;
    private static TransactionService transactionService;
    private static Map<TransactionType, TransactionHandler> strategyMap = Map.of(
            TransactionType.BALANCE, new BalanceHandler(),
            TransactionType.PURCHASE, new PurchaseHandler(),
            TransactionType.RETURN, new ReturnHandler(),
            TransactionType.SUPPLY, new SupplyHandler()
    );

    @Before
    public void setUp() {
        fruitMap = new HashMap<>();
        fruitDao = new FruitDaoImpl(fruitMap);
        transactionService = new TransactionServiceImpl(fruitDao, strategyMap);
    }

    @Test
    public void apply_Balance_Ok() {
        transactionService.apply(balanceTransaction);
        assertEquals("Storage size", 1, fruitMap.size());
        Fruit expectedFruit = new Fruit(balanceTransaction.getFruitName());
        Integer expectedAmount = balanceTransaction.getAmount();
        assertTrue("Storage contain '" + expectedFruit.getName() + "'",
                fruitMap.containsKey(expectedFruit));
        assertEquals("Storage '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
    }

    @Test
    public void apply_Purchase_Ok() {
        transactionService.apply(purchaseTransaction);
        assertEquals("Storage size", 1, fruitMap.size());
        Fruit expectedFruit = new Fruit(purchaseTransaction.getFruitName());
        Integer expectedAmount = -purchaseTransaction.getAmount();
        assertTrue("Storage contain '" + expectedFruit.getName() + "'",
                fruitMap.containsKey(expectedFruit));
        assertEquals("Storage '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
    }

    @Test
    public void apply_Return_Ok() {
        transactionService.apply(returnTransaction);
        assertEquals("Storage size", 1, fruitMap.size());
        Fruit expectedFruit = new Fruit(returnTransaction.getFruitName());
        Integer expectedAmount = returnTransaction.getAmount();
        assertTrue("Storage contain '" + expectedFruit.getName() + "'",
                fruitMap.containsKey(expectedFruit));
        assertEquals("Storage '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
    }

    @Test
    public void apply_Supply_Ok() {
        transactionService.apply(supplyTransaction);
        assertEquals("Storage size", 1, fruitMap.size());
        Fruit expectedFruit = new Fruit(supplyTransaction.getFruitName());
        Integer expectedAmount = supplyTransaction.getAmount();
        assertTrue("Storage contain '" + expectedFruit.getName() + "'",
                fruitMap.containsKey(expectedFruit));
        assertEquals("Storage '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
    }

    @Test
    public void apply_ZSequence_Ok() {
        transactionService.apply(balanceTransaction);
        assertEquals("Storage size", 1, fruitMap.size());
        Fruit expectedFruit = new Fruit(balanceTransaction.getFruitName());
        Integer expectedAmount = balanceTransaction.getAmount();
        assertTrue("Storage contain '" + expectedFruit.getName() + "'",
                fruitMap.containsKey(expectedFruit));
        assertEquals("Storage after balance '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
        transactionService.apply(purchaseTransaction);
        expectedAmount = expectedAmount - purchaseTransaction.getAmount();
        assertEquals("Storage after purchase '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
        transactionService.apply(returnTransaction);
        expectedAmount = expectedAmount + returnTransaction.getAmount();
        assertEquals("Storage after return '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
        transactionService.apply(supplyTransaction);
        expectedAmount = expectedAmount + supplyTransaction.getAmount();
        assertEquals("Storage after supply '" + expectedFruit.getName() + "'.Amount",
                expectedAmount, fruitMap.get(expectedFruit));
    }
}