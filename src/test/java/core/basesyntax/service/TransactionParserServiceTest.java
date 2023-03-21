package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceTest {
    private static final String INCORRECT_FIRSLINE_PATH =
            "src/test/resources/falseInput.csv";
    private static final String DEFAULT_DATA =
            "src/test/resources/input.csv";
    private static ReaderService readerService;
    private static TransactionParserService transactionParserService;
    private static List<FruitTransaction> expectedData;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
        transactionParserService = new TransactionParserServiceImpl();
        expectedData = List.of(new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "banana", 100));
    }

    @Test
    public void parseData_defaultInput_ok() {
        List<FruitTransaction> actualData =
                transactionParserService.parse(readerService.readFrom(DEFAULT_DATA));
        for (int i = 0; i < actualData.size(); i++) {
            assertEquals(expectedData.get(i).getOperation(), actualData.get(i).getOperation());
            assertEquals(expectedData.get(i).getFruit(), actualData.get(i).getFruit());
            assertEquals(expectedData.get(i).getQuantity(), actualData.get(i).getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseData_incorrect_notOk() {
        transactionParserService.parse(readerService.readFrom(INCORRECT_FIRSLINE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void parseData_isNull_notOk() {
        transactionParserService.parse(null);
    }
}
