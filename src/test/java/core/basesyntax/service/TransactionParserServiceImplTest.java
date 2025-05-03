package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static final int SIZE_OF_DEFAULT_FILE = 8;
    private static final String INPUT_FILE_HEADER = "operation,fruit,quantity";
    private static final String DEFAULT_WRONG_OPERATION = "c,apple,25";
    private static final String DEFAULT_EMPTY_FRUIT = "b,,25";
    private static final String DEFAULT_QUALITY_IS_WRONG_CHARACTER = "b,apple,k";
    private static final String FILE_NAME = "src/test/resources/input.csv";
    private static File testFile;
    private final TransactionParserService transactionParser
            = new TransactionParserServiceImpl();
    private final ReaderService readerService = new ReaderServiceImpl();

    @BeforeClass
    public static void setUpBeforeClass() {
        testFile = new File(FILE_NAME);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void createTransaction_ok() {
        List<String> infoInStrings = readerService.readFileToList(testFile);
        List<FruitTransaction> actual = transactionParser.createTransaction(infoInStrings);
        assertEquals("The method does not work correctly!",
                SIZE_OF_DEFAULT_FILE, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_wrongCode_ok() {
        List<String> input = new ArrayList<>();
        input.add(INPUT_FILE_HEADER);
        input.add(DEFAULT_WRONG_OPERATION);
        transactionParser.createTransaction(input);
        fail("Test failed! Unsupported operation!");
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_emptyFruit_ok() {
        List<String> input = new ArrayList<>();
        input.add(INPUT_FILE_HEADER);
        input.add(DEFAULT_EMPTY_FRUIT);
        transactionParser.createTransaction(input);
        fail("Test failed! Fruit cannot be empty!");
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_qualityIsWrongCharacter_ok() {
        List<String> input = new ArrayList<>();
        input.add(INPUT_FILE_HEADER);
        input.add(DEFAULT_QUALITY_IS_WRONG_CHARACTER);
        transactionParser.createTransaction(input);
        fail("Test failed! Quality must contain only digits!");
    }
}
