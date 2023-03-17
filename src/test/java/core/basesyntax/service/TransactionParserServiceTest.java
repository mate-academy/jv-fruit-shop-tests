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
    private static List<FruitTransaction> defaultData;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
        transactionParserService = new TransactionParserServiceImpl();
        defaultData = List.of(new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "banana", 100));
    }

    @Test
    public void parseData_defaultInput_ok() {
        List<FruitTransaction> parsedData =
                transactionParserService.parse(readerService.readFrom(DEFAULT_DATA));
        for (int i = 0; i < parsedData.size(); i++) {
            assertEquals(defaultData.get(i).getOperation(), parsedData.get(i).getOperation());
            assertEquals(defaultData.get(i).getFruit(), parsedData.get(i).getFruit());
            assertEquals(defaultData.get(i).getQuantity(), parsedData.get(i).getQuantity());
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
