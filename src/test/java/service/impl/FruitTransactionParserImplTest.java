package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitTransactionParserService;

public class FruitTransactionParserImplTest {
    private static final String PATH_TO_INPUT_FILE =
            "src/test/resources/InputDataForFruitTransactionParser.csv";
    private static List<String> DATA_FROM_FILE;
    private static List<FruitTransaction> EXPECTED_AFTER_PARSE;
    private static FruitTransactionParserService fruitTransactionParser
            = new FruitTransactionParserImpl();

    @BeforeClass
    public static void beforeClass() {
        try {
            DATA_FROM_FILE = Files.readAllLines(Path.of(PATH_TO_INPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Test exception. Can't read file: " + PATH_TO_INPUT_FILE);
        }
        // 20 + 100 - 13 + 5 = 125 - 13 = 112
        FruitTransaction fruitTransaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100);
        FruitTransaction fruitTransaction3 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13);
        FruitTransaction fruitTransaction4 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5);
        EXPECTED_AFTER_PARSE = List.of(fruitTransaction1,
                fruitTransaction2, fruitTransaction3, fruitTransaction4);
    }

    @Test
    public void parse_Ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(DATA_FROM_FILE);
        assertEquals(EXPECTED_AFTER_PARSE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalid_code_Ok() {
        List<String> dataFromFile = new ArrayList<>(DATA_FROM_FILE);
        dataFromFile.add("x,banana,100");
        fruitTransactionParser.parse(dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void parse_data_isNull_Ok() {
        fruitTransactionParser.parse(null);
    }
}
