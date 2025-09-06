package core.basesyntax.fruittransactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    public static final Storage storage = new Storage();
    public static final OperationHandler purchaseHandler = new PurchaseHandler(storage);
    public static final FruitTransaction fruitTransaction = new FruitTransaction();
    public static final OperationHandler balanceHandler = new BalanceHandler(storage);
    public static final FruitTransaction fruitTransactionNo1 = new FruitTransaction();
    public static final FruitTransaction fruitTransactionNo2 = new FruitTransaction();

    @AfterEach
    public void afterEach() {
        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }

    @Test
    void handle_nullFruitTransaction_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(null));
    }

    @Test
    void handle_ResultOfSubtractionWithDefaultValueIsNegative_notOk() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.P);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(fruitTransaction));
    }

    @Test
    void handle_includeValueOfPurchaseToStorage_ok() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransactionNo1.setFruit("apple");
        fruitTransactionNo1.setQuantity(50);
        balanceHandler.handle(fruitTransactionNo1);
        fruitTransactionNo2.setTypeOfTransaction(TypeOfTransaction.P);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(20);
        purchaseHandler.handle(fruitTransactionNo2);
        Assertions.assertEquals(30, storage
                .getDataStorageMapOrDefault("apple").get("apple"));
    }

    @Test
    void handle_ResultOfSubtractionValueIsNegative_notOk() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransactionNo1.setFruit("apple");
        fruitTransactionNo1.setQuantity(50);
        balanceHandler.handle(fruitTransactionNo1);
        fruitTransactionNo2.setTypeOfTransaction(TypeOfTransaction.P);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(100);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(fruitTransactionNo2));
    }

    @Test
    void handle_includeValueOfPurchaseToStorageWhenResultOfSubtractionIsZero_ok() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransactionNo1.setFruit("apple");
        fruitTransactionNo1.setQuantity(50);
        balanceHandler.handle(fruitTransactionNo1);
        fruitTransactionNo2.setTypeOfTransaction(TypeOfTransaction.P);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(50);
        purchaseHandler.handle(fruitTransactionNo2);
        Assertions.assertEquals(0, storage
                .getDataStorageMapOrDefault("apple").get("apple"));
    }
}
