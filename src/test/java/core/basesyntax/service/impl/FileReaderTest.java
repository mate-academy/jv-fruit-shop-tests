package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String PATH =
            "src/test/resources/testInputFile.csv";
    private static final String INCORRECT_PATH =
            "src/test/resources/testNoInputFile.csv";
    private static final String EMPTY_PATH =
            "src/test/resources/testEmptyFile.csv";
    private static final String WRONG_PATH
            = "src/test/rhgfh/testInputFile.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @BeforeClass
    public static void beforeClass() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
        String incorrectData = "Incorrect data";
        try {
            Files.write(Paths.get(PATH), input.getBytes());
            Files.write(Paths.get(INCORRECT_PATH), incorrectData.getBytes());
            Files.write(Paths.get(EMPTY_PATH), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data" + e);
        }
    }

    @Test
    public void readFile_emptyData_ok() {
        assertTrue(fileReader.read(EMPTY_PATH).isEmpty());
    }

    @Test (expected = RuntimeException.class)
    public void readFile_incorrectPath_notOk() {
        fileReader.read(INCORRECT_PATH);
        fileReader.read(WRONG_PATH);
    }

    @Test
    public void readFile_correctData_ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Transaction.Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new Transaction(Transaction.Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new Transaction(Transaction.Operation.SUPPLY, new Fruit("banana"), 100));
        expected.add(new Transaction(Transaction.Operation.PURCHASE, new Fruit("banana"), 13));
        expected.add(new Transaction(Transaction.Operation.RETURN, new Fruit("apple"), 10));
        expected.add(new Transaction(Transaction.Operation.PURCHASE, new Fruit("apple"), 20));
        expected.add(new Transaction(Transaction.Operation.PURCHASE, new Fruit("banana"), 5));
        expected.add(new Transaction(Transaction.Operation.SUPPLY, new Fruit("banana"), 50));
        List<Transaction> actual = fileReader.read(PATH);
        assertEquals(expected.toString(), actual.toString());
    }
}



