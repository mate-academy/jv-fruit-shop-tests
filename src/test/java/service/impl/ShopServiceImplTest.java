package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.Test;
import service.Converter;
import service.FileReader;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

public class ShopServiceImplTest {
    private static final String TEST_FILE = "src/test/java/resources/test.csv";

    @Test
    void processValidTransaction_ok() {
        Storage.clearStorage();
        Map<Transaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        handlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        handlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(Transaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlerMap);
        Map<String, Integer> fruitList = new HashMap<>();
        ShopServiceImpl service = new ShopServiceImpl(strategy, fruitList);
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.read(TEST_FILE);
        Converter converter = new ConverterImpl();
        List<Transaction> transactionList = converter.convertTransaction(data);

        service.process(transactionList);

        assertEquals(195, Storage.getAmount("apple"));
        assertEquals(30, Storage.getAmount("orange"));
    }
}
