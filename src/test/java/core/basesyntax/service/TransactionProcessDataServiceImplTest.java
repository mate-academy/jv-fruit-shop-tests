package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.BalanceTransactionHandler;
import core.basesyntax.service.transaction.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.ReturnTransactionHandler;
import core.basesyntax.service.transaction.SupplyTransactionHandler;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessDataServiceImplTest {
    private static final String FILE_PATH = "src/main/java/core/basesyntax/db/source.csv";
    private static final String INCORRECT_OPERATION_FILE_PATH =
            "src/main/java/core/basesyntax/db/sourceIncorrectOperation.csv";
    private static final String INCORRECT_FRUIT_FILE_PATH =
            "src/main/java/core/basesyntax/db/sourceIncorrectFruit.csv";
    private static final String INCORRECT_QUANTITY_FILE_PATH =
            "src/main/java/core/basesyntax/db/sourceIncorrectQuantity.csv";
    private static final String HEAD_VALUE = "type,fruit,quantity";
    private static TransactionProcessDataService transactionProcessDataService;
    private static Reader reader = new ReaderImpl();

    private static Map<FruitTransaction.Operation, TransactionHandler> createTransactionHandler() {
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap =
                new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        return transactionHandlerMap;
    }

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandler
                = createTransactionHandler();
        transactionProcessDataService =
                new TransactionProcessDataServiceImpl(transactionHandler);
    }

    @Test
    void process_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> processData = transactionProcessDataService.processData(null);
        });
    }

    @Test
    void process_emptyData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> processData =
                    transactionProcessDataService.processData(Collections.EMPTY_LIST);
        });
    }

    @Test
    void process_dataWithOneValue() {
        List<String> data = new ArrayList<>();
        data.add(HEAD_VALUE);
        Map<String, Integer> processData = transactionProcessDataService.processData(data);
        assertTrue(processData.isEmpty());
    }

    @Test
    void process_dataWithIncorrectOperation() {
        List<String> fromFile = reader.readFromFile(INCORRECT_OPERATION_FILE_PATH);
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> processData = transactionProcessDataService.processData(fromFile);;
        });
    }

    @Test
    void process_dataWithIncorrectFruit() {
        List<String> fromFile = reader.readFromFile(INCORRECT_FRUIT_FILE_PATH);
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> processData = transactionProcessDataService.processData(fromFile);;
        });
    }

    @Test
    void process_dataWithIncorrectQuantity() {
        List<String> fromFile = reader.readFromFile(INCORRECT_QUANTITY_FILE_PATH);
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> processData = transactionProcessDataService.processData(fromFile);;
        });
    }

    @Test
    void process_validDara_ok() {
        List<String> fromFile = reader.readFromFile(FILE_PATH);
        Map<String, Integer> processData = transactionProcessDataService.processData(fromFile);
        boolean isValid = processData != null && !processData.isEmpty();
        assertTrue(isValid);
    }
}
