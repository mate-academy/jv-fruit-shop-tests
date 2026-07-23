package core.basesyntax;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConvertorImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.PurchaseOperation;
import core.basesyntax.service.strategy.ReturnOperation;
import core.basesyntax.service.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitServiceTest {

    private Storage storage;
    private DataConvertorImpl convertor;
    private FileReaderImpl reader;
    private ReportGeneratorImpl reporter;
    private OperationStrategyImpl strategy;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        storage = new Storage();
        reader = new FileReaderImpl();
        convertor = new DataConvertorImpl();
        reporter = new ReportGeneratorImpl();
        shopService = new ShopServiceImpl(strategy);

    }

    @Test
    void testFileReaderIsOk() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");

        List<String> actual = reader.read("inputTestFile.txt");

        assertEquals(expected, actual);
    }

    @Test
    void read_NullFileName_NotOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reader.read(null)
        );

        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void dataConvertorIsOk() {
        List<FruitTransaction> expectedFruitList = new ArrayList<>();
        expectedFruitList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                20));
        expectedFruitList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",
                100));

        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,100");

        List<FruitTransaction> actualFruitList = convertor.convertToTransaction(testList);

        assertEquals(expectedFruitList, actualFruitList);
    }

    @Test
    void fileReaderIsEmpty() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reader.read(null)
        );

        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void convertToTransaction_Ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50"
        );

        List<FruitTransaction> result = convertor.convertToTransaction(input);

        assertEquals(2, result.size());

        assertEquals(FruitTransaction.Operation.BALANCE,
                result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY,
                result.get(1).getOperation());
    }

    @Test
    void convertToTransaction_NullList_NotOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> convertor.convertToTransaction(null)
        );

        assertEquals("List can not be null", exception.getMessage());
    }

    @Test
    void getHandler_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers
                = new HashMap<>();

        OperationHandler supplyHandler = new SupplyOperation();

        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);

        OperationStrategy strategy
                = new OperationStrategyImpl(handlers);

        assertEquals(
                supplyHandler,
                strategy.getHandler(FruitTransaction.Operation.SUPPLY)
        );
    }

    @Test
    void getReport_Ok() {
        Storage.getFruits().put("apple", 100);
        Storage.getFruits().put("banana", 100);

        ReportGenerator reportGenerator
                = new ReportGeneratorImpl();

        String expected
                = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();

        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void applyForSuply_Ok() {
        Storage.getFruits().clear();

        SupplyOperation operation = new SupplyOperation();

        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "apple",
                        100
                );

        operation.apply(transaction);

        assertEquals(
                100,
                Storage.getFruits().get("apple")
        );
    }

    @Test
    void applyForReturn_Ok() {
        Storage.getFruits().clear();
        Storage.getFruits().put("apple", 100);

        ReturnOperation operation = new ReturnOperation();

        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        "apple",
                        20
                );

        operation.apply(transaction);

        assertEquals(
                120,
                Storage.getFruits().get("apple")
        );
    }

    @Test
    void applyForPurchase_Ok() {
        Storage.getFruits().clear();
        Storage.getFruits().put("apple", 100);

        PurchaseOperation operation = new PurchaseOperation();

        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "apple",
                        40
                );

        operation.apply(transaction);

        assertEquals(
                60,
                Storage.getFruits().get("apple")
        );
    }

    @Test
    void apply_NegativeBalance_NotOk() {
        Storage.getFruits().clear();

        PurchaseOperation operation = new PurchaseOperation();

        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "apple",
                        100
                );

        assertThrows(
                RuntimeException.class,
                () -> operation.apply(transaction)
        );
    }

    @Test
    void process_Ok() {
        Storage.getFruits().clear();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        ShopService shopService = new ShopServiceImpl(strategy);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "apple",
                        100
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "apple",
                        50
                )
        );

        shopService.process(transactions);

        assertEquals(150, Storage.getFruits().get("apple"));
    }
}
