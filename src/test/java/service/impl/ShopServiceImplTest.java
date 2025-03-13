package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;
import service.FileReader;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

class ShopServiceImplTest {
    private static final String TEST_DATA_FILE = "src/test/java/resources/test.csv";

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        FileReader fileReader = new FileReaderImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        List<String> csvData = null;
        try {
            csvData = fileReader.read(TEST_DATA_FILE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(csvData);

        shopService.process(transactions);
    }

    @Test
    public void processValidTransactions_ok() {
        assertEquals(90, Storage.getAmount("apple"));
        assertEquals(152, Storage.getAmount("banana"));
    }
}
