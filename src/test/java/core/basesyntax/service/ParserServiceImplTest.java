package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/input.csv";
    private ReaderServiceImpl readerService;
    private ParserServiceImpl parserService;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
        parserService = new ParserServiceImpl();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
    }

    @Test
    public void parserService_parseFile_Ok() {
        List<String> testReading = readerService.read(INPUT_FILE_PATH);
        List<FruitTransaction> actualResult = parserService.parse(testReading);
        Assert.assertEquals("Test failed! You should returned next fruit "
                        + fruitTransaction.getFruit() + " but you returned "
                        + actualResult.get(0).getFruit(),
                fruitTransaction, actualResult.get(0));
    }
}
