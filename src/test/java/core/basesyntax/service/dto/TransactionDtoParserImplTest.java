package core.basesyntax.service.dto;

import core.basesyntax.exeptions.InvalidDataFormatException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoParserImplTest {
    private static final String PATH_TO_FILE
            = "src/test/resources/data_test_ok.csv";
    private static final String PATH_TO_INVALID_FILE
            = "src/test/resources/invalid_data_format.csv";
    private static final String PATH_TO_INVALID_FRUIT_FILE
            = "src/test/resources/invalid_fruit.csv";
    private static final String PATH_TO_INVALID_OPERATION_FILE
            = "src/test/resources/invalid_operation_type.csv";
    private static final String PATH_TO_INVALID_QUANTITY_FILE
            = "src/test/resources/invalid_quantity.csv";
    private static TransactionDtoParser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new TransactionDtoParserImpl();
    }

    @Test
    public void transactionDtoParserImplTest_parse_Ok() throws IOException {
        List<String> transactions = Files.readAllLines(Path.of(PATH_TO_FILE));
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        expected.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
        expected.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13));
        expected.add(new TransactionDto(OperationType.RETURN, new Fruit("apple"), 10));
        List<TransactionDto> actual = parser.parse(transactions);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = InvalidDataFormatException.class)
    public void transactionDtoParserImplTest_parseInvalidFile_NotOk() throws IOException {
        List<String> transactions = Files.readAllLines(Path.of(PATH_TO_INVALID_FILE));
        parser.parse(transactions);
    }

    @Test (expected = InvalidDataFormatException.class)
    public void transactionDtoParserImplTest_parseInvalidFruit_NotOk() throws IOException {
        List<String> transactions = Files.readAllLines(Path.of(PATH_TO_INVALID_FRUIT_FILE));
        parser.parse(transactions);
    }

    @Test (expected = InvalidDataFormatException.class)
    public void transactionDtoParserImplTest_parseInvalidOperation_NotOk() throws IOException {
        List<String> transactions = Files.readAllLines(Path.of(PATH_TO_INVALID_OPERATION_FILE));
        parser.parse(transactions);
    }

    @Test (expected = InvalidDataFormatException.class)
    public void transactionDtoParserImplTest_parseInvalidQuantity_NotOk() throws IOException {
        List<String> transactions = Files.readAllLines(Path.of(PATH_TO_INVALID_QUANTITY_FILE));
        parser.parse(transactions);
    }
}
