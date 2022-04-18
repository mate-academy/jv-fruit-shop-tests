package core.basesyntax.service;

import core.basesyntax.exceptions.EmptyFileException;
import core.basesyntax.exceptions.ExistFileException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderService reader;
    private static final String INPUT_FILE = "src/test/resources/InputData.csv";
    private static final String EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private static final String WRONG_FILE = "src/test/resources/WrongFile.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_emptyFile_notOk() {
        Assertions.assertThrows(EmptyFileException.class,
                () -> reader.readFromFile(EMPTY_FILE));
    }

    @Test
    public void readFromFile_nullFile_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> reader.readFromFile(null));
    }

    @Test
    public void readFromFile_wrongFile_notOk() {
        Assertions.assertThrows(ExistFileException.class,
                () -> reader.readFromFile(WRONG_FILE));
    }

    @Test
    public void readFromFile_properFile_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = reader.readFromFile(INPUT_FILE);
        Assertions.assertEquals(actual, expected);
    }
}
