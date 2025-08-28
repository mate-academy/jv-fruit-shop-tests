package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.CsvFileReader;
import service.FruitDao;

class CsvFileReaderImplTest {
    private static CsvFileReader fileReader;
    private static FruitDao fruitDao;

    @BeforeAll
    static void setUp() {
        fileReader = new CsvFileReaderImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void read_NullFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(null, fruitDao));
    }

    @Test
    void read_FilePathIsEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("", fruitDao));
    }

    @Test
    void read_ValidInput_Ok() {
        String filePath = "src/test/resources/test_input.csv";
        List<FruitTransaction> fruitTransactionList = fileReader.read(filePath, fruitDao);

        assertNotNull(fruitTransactionList);
        assertFalse(fruitTransactionList.isEmpty());

        FruitTransaction firstFruit = fruitTransactionList.get(0);
        assertEquals("banana", firstFruit.getFruit());
        assertEquals(50, firstFruit.getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, firstFruit.getOperation());
    }

    @Test
    void read_EmptyFile_notOk() {
        String filePath = "src/test/resources/test_empty.csv";

        List<FruitTransaction> fruitTransactionList = fileReader.read(filePath, fruitDao);

        assertNotNull(fruitTransactionList);
        assertTrue(fruitTransactionList.isEmpty());
    }
}
