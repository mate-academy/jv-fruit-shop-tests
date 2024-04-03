package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    public static final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl();
    public static final int CORRECT_BANANA_QUANTITY_IN_STORAGE = 15;
    public static final int CORRECT_APPLE_QUANTITY_IN_STORAGE = 10;
    public static final List<FruitTransaction> CORRECT_INPUT_DATA = Arrays.asList(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",20),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana",10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana",15),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",20),
            new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",10));

    public static final List<FruitTransaction> NULL_TRANSACTION_DATA = Arrays.asList(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",20),
            null);

    @Test
    void distribute_correctData_ok() {
        TRANSACTION_SERVICE.distribute(CORRECT_INPUT_DATA);
        Integer actualBanana = FruitStorage.FRUIT_STORAGE.get("banana");
        Integer actualApple = FruitStorage.FRUIT_STORAGE.get("apple");
        assertEquals(CORRECT_BANANA_QUANTITY_IN_STORAGE, actualBanana);
        assertEquals(CORRECT_APPLE_QUANTITY_IN_STORAGE, actualApple);
    }

    @Test
    void distribute_nullData_notOk() {
        assertThrows(RuntimeException.class,
                () -> TRANSACTION_SERVICE.distribute(NULL_TRANSACTION_DATA));
        assertThrows(RuntimeException.class,() -> TRANSACTION_SERVICE.distribute(null));
    }

    @Test
    void distribute_illegalOperationData_notOk() {
        assertThrows(RuntimeException.class,
                () -> TRANSACTION_SERVICE.distribute(List.of(
                        new FruitTransaction(FruitTransaction.Operation.fromCode("illegal"),
                                "banana", 20)
                )));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.FRUIT_STORAGE.clear();
    }
}
