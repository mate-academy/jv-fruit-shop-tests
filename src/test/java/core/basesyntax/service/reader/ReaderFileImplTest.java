package core.basesyntax.service.reader;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ReaderFileImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String EMPTY_FILE = "src/main/resources/empty.csv";
    private static final String NEGATIVE_FRUIT = "src/main/resources/negativeFruit.csv";
    private static final String NOT_EXIST_FILE = "src/test/resources/notExist.csv";
    private ReaderFile readerFile;
    private List<FruitTransaction> fruitList;

    @BeforeEach
    void setUp() {
        readerFile = new ReaderFileImpl();
        fruitList = new ArrayList<>();
    }

    @Test
    void fileIsEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(EMPTY_FILE);
        });
    }

    @Test
    void fileIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(NOT_EXIST_FILE);
        });
    }

    @Test
    void quantityInFileLessZero_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(NEGATIVE_FRUIT);
        });
    }

    @Test
    void fileReaderWorkCorrectly_ok() {
        fruitList.add(new FruitTransaction(Operation.BALANCE,"banana",20));
        fruitList.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        fruitList.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        fruitList.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
        fruitList.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        fruitList.add(new FruitTransaction(Operation.PURCHASE, "apple", 40));
        fruitList.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
        fruitList.add(new FruitTransaction(Operation.SUPPLY, "banana", 50));

        Assertions.assertDoesNotThrow(() -> {
            List<FruitTransaction> fruitTransactionList = readerFile.readFile(INPUT_FILE);
            fruitTransactionList.equals(fruitList);
        });
    }
}
