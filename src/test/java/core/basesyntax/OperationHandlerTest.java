package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {

    private static BalanceOperationHandler balanceOperationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static ReturnOperationHandler returnOperationHandler;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();

        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
        supplyOperationHandler = new SupplyOperationHandler(fruitDao);

    }

    @BeforeEach
    void setUpBeforeEach() {
        FruitStorage.fruits.clear();
    }

    @Test
    void balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);

        balanceOperationHandler.handle(transaction);

        assertTrue(FruitStorage.fruits.containsKey(transaction.getFruit()), "");
        assertTrue(FruitStorage.fruits.containsValue(transaction.getQuantity()));
    }

    @Test
    void returnOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100);

        returnOperationHandler.handle(transaction);

        assertTrue(FruitStorage.fruits.containsKey(transaction.getFruit()));
        assertTrue(FruitStorage.fruits.containsValue(transaction.getQuantity()));
    }

    @Test
    void supplyOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100);

        supplyOperationHandler.handle(transaction);

        assertTrue(FruitStorage.fruits.containsKey(transaction.getFruit()));
        assertTrue(FruitStorage.fruits.containsValue(transaction.getQuantity()));
    }

    @Test
    void purchaseOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 6);

        FruitStorage.fruits.put("banana", 12);

        int expected = FruitStorage.fruits.get(transaction.getFruit()) - transaction.getQuantity();

        purchaseOperationHandler.handle(transaction);

        assertEquals(expected, FruitStorage.fruits.get(transaction.getFruit()));
    }

    @Test
    void purchaseOperation_purchaseMoreThanInStorage_throwException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 6);

        FruitStorage.fruits.put("banana", 3);

        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(transaction));
    }

    @Test
    void purchaseOperation_purchaseMarginalValue_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 6);

        FruitStorage.fruits.put("banana", 6);

        int expected = FruitStorage.fruits.get(transaction.getFruit()) - transaction.getQuantity();

        purchaseOperationHandler.handle(transaction);

        assertEquals(expected, FruitStorage.fruits.get(transaction.getFruit()));
    }

}
