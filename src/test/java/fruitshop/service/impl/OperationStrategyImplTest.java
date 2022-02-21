package fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import fruitshop.db.Storage;
import fruitshop.model.Operation;
import fruitshop.service.OperationStrategy;
import fruitshop.service.ReaderService;
import fruitshop.service.WriterService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy = new OperationStrategyImpl();
    private static ReaderService readerService = new ReaderServiceImpl();
    private static WriterService writerService = new WriterServiceImpl();
    private static FruitServiceImpl fruitService = new FruitServiceImpl(readerService,
            writerService, operationStrategy);

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitService.putStrategies();
    }

    @Test
    public void chooseStrategy_ok() {
        Storage.fruitList.put("apple", 10);
        operationStrategy.chooseStrategy(Operation.SUPPLY, "apple", 20);
        assertEquals(Integer.valueOf(30), Storage.fruitList.get("apple"));
    }

    @Test
    public void chooseStrategy_notExistingFruit_ok() {
        Storage.fruitList.put("apple", 10);
        exceptionRule.expect(NullPointerException.class);
        operationStrategy.chooseStrategy(Operation.PURCHASE, "banana", 20);
    }

    @After
    public void tearDown() {
        Storage.fruitList.clear();
    }
}
