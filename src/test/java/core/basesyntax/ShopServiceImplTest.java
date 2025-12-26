package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverterImpl;
import service.FileReaderImpl;
import service.ShopService;
import service.ShopServiceImpl;
import strategy.BalanceOperation;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

class ShopServiceImplTest {
    private static final String INPUT_FILE = "src/test/java/resource/test_data.csv";

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();

        Map<FruitTransaction.Operation, OperationHandler> strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategies.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategies);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_ValidData_Ok() {
        FileReaderImpl fileReader = new FileReaderImpl();
        DataConverterImpl dataConverter = new DataConverterImpl();
        shopService.process(dataConverter.convertToTransaction(fileReader.read(INPUT_FILE)));

        assertEquals(107, Storage.fruitStorage.get("banana"));
        assertEquals(120, Storage.fruitStorage.get("apple"));
    }
}
