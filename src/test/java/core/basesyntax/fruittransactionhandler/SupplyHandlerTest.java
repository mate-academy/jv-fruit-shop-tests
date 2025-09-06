package core.basesyntax.fruittransactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final Storage storage = new Storage();
    private static final OperationHandler supplyHandler = new SupplyHandler(storage);
    private static final FruitTransaction fruitTransaction = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo1 = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo2 = new FruitTransaction();

    @AfterEach
    public void afterEach() {

        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }
    @Test
    void handle_nullFruitTransaction_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyHandler.handle(null));
    }

    @Test
    void handle_addValueToStorage_ok() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransactionNo1.setFruit("orange");
        fruitTransactionNo1.setQuantity(25);
        fruitTransactionNo2.setTypeOfTransaction(TypeOfTransaction.R);
        fruitTransactionNo2.setFruit("orange");
        fruitTransactionNo2.setQuantity(20);
        supplyHandler.handle(fruitTransactionNo1);
        supplyHandler.handle(fruitTransactionNo2);
        Assertions.assertEquals(45, storage.getDataStorageMap().get("orange"));
    }

    @Test
    void handle_FruitNonExistInStorage_returnZeroValue_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.R);
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(20);
        supplyHandler.handle(fruitTransaction);
        Assertions.assertEquals(20, storage.getDataStorageMap().get("orange"));
    }
}
