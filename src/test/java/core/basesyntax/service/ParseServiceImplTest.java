package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {
    private static FileReader fileReader;
    private static ParseService parseService;
    private static final String INPUT_1_FILE_NAME = "input1.csv";
    private static final String INPUT_2_FILE_NAME = "input2.csv";
    private static final String INPUT_3_FILE_NAME = "input3.csv";
    private static final String INVALID_OPERATION_FILE_NAME = "invalid1.csv";
    private static final String INVALID_QUANTITY_FILE_NAME = "invalid2.csv";
    private static ClassLoader classLoader;

    @BeforeAll
    static void beforeAll() {
        classLoader = FileReaderImplTest.class.getClassLoader();
        fileReader = new FileReaderImpl();
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseInput1File_Ok() {
        URL resources = classLoader.getResource(INPUT_1_FILE_NAME);
        List<String> result = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 13, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("apple", 20, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 50, FruitTransaction.Operation.SUPPLY)));
        List<FruitTransaction> actual = parseService.parse(result);
        assertEquals(expected, actual);
    }

    @Test
    void parseInput2File_Ok() {
        URL resources = classLoader.getResource(INPUT_2_FILE_NAME);
        List<String> result = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("banana", 200, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 25, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 20, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY)));
        List<FruitTransaction> actual = parseService.parse(result);
        assertEquals(expected, actual);
    }

    @Test
    void parseInput3File_Ok() {
        URL resources = classLoader.getResource(INPUT_3_FILE_NAME);
        List<String> result = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("pear", 200, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("pear", 80, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 25, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("pear", 20, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 20, FruitTransaction.Operation.PURCHASE)));
        List<FruitTransaction> actual = parseService.parse(result);
        assertEquals(expected, actual);
    }

    @Test
    void parseInvalidOperationFile_Not_Ok() {
        URL resources = classLoader.getResource(INVALID_OPERATION_FILE_NAME);
        List<String> result = fileReader.readFromFile(new File(resources.getFile()));
        assertThrows(RuntimeException.class, () -> parseService.parse(result));
    }

    @Test
    void parseInvalidQuantityFile_Not_Ok() {
        URL resources = classLoader.getResource(INVALID_QUANTITY_FILE_NAME);
        List<String> result = fileReader.readFromFile(new File(resources.getFile()));
        assertThrows(RuntimeException.class, () -> parseService.parse(result));
    }
}
