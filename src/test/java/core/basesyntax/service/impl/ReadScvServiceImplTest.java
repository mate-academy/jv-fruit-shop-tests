package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReadScvService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadScvServiceImplTest {
    private static final String FILE_PATH = "inputTestFile.csv";
    private static final String FRUIT_NAME = "banana";
    private ReadScvService readScvService;
    private List<FruitTransaction> actualFruitTransactionList;

    private FruitTransaction createFruitTransaction(String fruit, int quantity,
                                                    String operation) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(FruitTransaction
                .Operation.fromCode(operation));
        return fruitTransaction;
    }

    @BeforeEach
    void setUp() {
        readScvService = new ReadScvServiceImpl();
        actualFruitTransactionList = new ArrayList<>();
        actualFruitTransactionList.add(createFruitTransaction(FRUIT_NAME, 100, "b"));
        actualFruitTransactionList.add(createFruitTransaction(FRUIT_NAME, 100, "s"));
        actualFruitTransactionList.add(createFruitTransaction(FRUIT_NAME, 13, "p"));
        actualFruitTransactionList.add(createFruitTransaction(FRUIT_NAME, 50, "r"));
    }

    @Test
    void readFromFile_ok() {
        List<FruitTransaction> fruitTransactionList = readScvService.readFromFile(FILE_PATH);
        assertEquals(actualFruitTransactionList, fruitTransactionList);
    }

    @Test
    void readFromFile_notOk() {
        String filePath = "/non/existent/directory/testFile.csv";
        assertThrows(RuntimeException.class, () -> readScvService.readFromFile(filePath));
    }
}
