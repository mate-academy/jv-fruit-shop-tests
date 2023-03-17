package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static FileReaderService fileReaderService;
    private static TransactionParserService transactionParserService;
    private static final String NORMAL_FILE =
            "/Users/MacBook/Desktop/base_1/jv-fruit-shop-tests/src/test/resources/input.csv";
    private static final String HEAD = "operation,fruit,quantity";
    private static final int AMOUNT_OF_LINES = 8;
    private static final String TRANSACTION_WITH_INVALID_COMMAND = "v, banana, 11";
    private static final String TRANSACTION_WITH_INVALID_AMOUNT_OF_PARTS = "v, 11";
    private static final int KEY_FOR_INVALID_TRANSACTION = 11;
    private static final String TRANSACTION_WITH_INVALID_AMOUNT = "v, banana, banana";

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        transactionParserService = new TransactionParserServiceImpl();
    }

    @After
    public void cleanStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void parseTransaction_Ok() {
        List<String> parsedTransaction = fileReaderService.readFromFile(NORMAL_FILE);
        List<FruitTransaction> actual = transactionParserService
                .parseFileInformation(parsedTransaction);
        assertEquals(AMOUNT_OF_LINES, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_qualityIsWrongCharacter_Ok() {
        List<String> input = new ArrayList<>();
        input.add(HEAD);
        input.add(TRANSACTION_WITH_INVALID_AMOUNT);
        transactionParserService.parseFileInformation(input);
    }

    @Test(expected = RuntimeException.class)
    public void parseTransaction_withInvalidCommand_notOk() {
        List<String> input = new ArrayList<>();
        input.add(HEAD);
        input.add(TRANSACTION_WITH_INVALID_COMMAND);
        transactionParserService.parseFileInformation(input);
    }

    @Test
    public void parseTransaction_withInvalidAmountOfParts_notOk() {
        List<String> input = new ArrayList<>();
        input.add(HEAD);
        input.add(TRANSACTION_WITH_INVALID_AMOUNT_OF_PARTS);
        transactionParserService.parseFileInformation(input);
        assertFalse(Storage.getFruits().containsKey(KEY_FOR_INVALID_TRANSACTION));
    }
}
