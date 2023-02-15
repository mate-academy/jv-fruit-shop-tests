package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private TransactionService transactionService;
    private List<String> elementsInFile;

    private FruitTransaction fruitTransaction;

    private FruitTransaction fruitTransaction2;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl();
        elementsInFile = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
        fruitTransaction2 = new FruitTransaction();
        elementsInFile.add("b,banana,20");
        elementsInFile.add("b,apple,100");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);
    }

    @Test
    void convertStringToFruitTransaction_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction);
        expected.add(fruitTransaction2);
        List<FruitTransaction> actual
                = transactionService.convertStringToFruitTransaction(elementsInFile);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertString_emptyElements_notOk() {
        List<String> elementsInFile = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, ()
                -> transactionService.convertStringToFruitTransaction(elementsInFile));
    }

    @Test
    void convertString_nullElements_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> transactionService.convertStringToFruitTransaction(null));
    }
}
