package core.basesyntax.service.parser;

import core.basesyntax.exception.EntryFormatException;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Product;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.parser.impl.TransactionDtoParserImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoParserTest {
    private static final String PATH_TO_FILE_INPUT = "src/main/resources/input.csv";
    private static final String PATH_TO_INCORRECT_OPERATION_FILE =
            "src/main/resources/incorrect_operation.csv";
    private static final String PATH_TO_INCORRECT_PRODUCT_FILE =
            "src/main/resources/incorrect_product.csv";
    private static final String PATH_TO_INCORRECT_AMOUNT_FILE =
            "src/main/resources/incorrect_amount.csv";
    private static final String PATH_TO_INCORRECT_FILE =
            "src/main/resources/incorrect_file.csv";
    private static TransactionDtoParser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new TransactionDtoParserImpl();
    }

    @Test
    public void parseDataFromFile_isOk() throws IOException {
        List<String> records = Files.readAllLines(Path.of(PATH_TO_FILE_INPUT));
        List<TransactionDto> exceptedList = new ArrayList<>();

        exceptedList.add(new TransactionDto(Operation.BALANCE,
                new Product("banana"), 150));
        exceptedList.add(new TransactionDto(Operation.SUPPLY,
                new Product("apple"), 10));
        exceptedList.add(new TransactionDto(Operation.PURCHASE,
                new Product("banana"), 30));

        List<TransactionDto> actualList = parser.parse(records);
        Assert.assertEquals(exceptedList, actualList);
    }

    @Test(expected = EntryFormatException.class)
    public void parseDataFromFiletWithIncorrectOperation_notOk() throws IOException {
        List<String> records = Files.readAllLines(Path.of(PATH_TO_INCORRECT_OPERATION_FILE));
        parser.parse(records);
    }

    @Test(expected = EntryFormatException.class)
    public void parseDataFromFileWithIncorrectProduct_notOk() throws IOException {
        List<String> records = Files.readAllLines(Path.of(PATH_TO_INCORRECT_PRODUCT_FILE));
        parser.parse(records);
    }

    @Test(expected = EntryFormatException.class)
    public void parseDataFromFileWithIncorrectAmount_notOk() throws IOException {
        List<String> records = Files.readAllLines(Path.of(PATH_TO_INCORRECT_AMOUNT_FILE));
        parser.parse(records);
    }

    @Test(expected = EntryFormatException.class)
    public void parseDataFromFileWithIncorrectFileDataFormat_notOk() throws IOException {
        List<String> records = Files.readAllLines(Path.of(PATH_TO_INCORRECT_FILE));
        parser.parse(records);
    }
}
