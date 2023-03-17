package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationImpl;
import core.basesyntax.strategy.impl.ChooseStrategyHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationImpl;
import core.basesyntax.strategy.impl.ReturnOperationImpl;
import core.basesyntax.strategy.impl.SupplyOperationImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ChooseStrategyHandlerImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static ChooseStrategyHandler chooseStrategyHandler;
    private static Map<FruitTransaction.Operation, OperationHandler> mapOfOperations;

    @BeforeAll
    static void beforeAll() {
        mapOfOperations = new HashMap<>();
        mapOfOperations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        chooseStrategyHandler = new ChooseStrategyHandlerImpl(mapOfOperations);
    }

    @Test
    void get_Supply_isOK() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        assertEquals(chooseStrategyHandler.getHandler(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, KEY, VALUE)
        ).getClass(), SupplyOperationImpl.class);
    }

    @Test
    void get_Purchase_isOK() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        assertEquals(chooseStrategyHandler.getHandler(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY, VALUE)
        ).getClass(), PurchaseOperationImpl.class);
    }

    @Test
    void get_Return_isOK() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        assertEquals(chooseStrategyHandler.getHandler(
                new FruitTransaction(FruitTransaction.Operation.RETURN, KEY, VALUE)
        ).getClass(), ReturnOperationImpl.class);
    }

    @Test
    void get_Balance_isOK() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        assertEquals(chooseStrategyHandler.getHandler(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, KEY, VALUE)
        ).getClass(), BalanceOperationImpl.class);
    }

}
