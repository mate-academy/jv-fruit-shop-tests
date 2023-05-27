package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.process.FruitTransactionService;
import core.basesyntax.process.FruitTransactionServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceTests {
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;
    private static final int EXPECTED_STORAGE_SIZE = 2;
    private static final int BANANA_AMOUNT = 120;
    private static final int APPLE_AMOUNT = 30;
    private static final String EMPTY_FRUIT_DATA = "";
    private static final String NULL_FRUIT_DATA = null;
    private static final String FRUIT_DATA =
            "b,banana,100" + System.lineSeparator()
                    + "b,apple,30" + System.lineSeparator()
                    + "r,banana,10" + System.lineSeparator()
                    + "p,banana,10" + System.lineSeparator()
                    + "s,banana,20" + System.lineSeparator();
    private static List<FruitTransaction> FRUIT_TRANSACTION_LIST;
    private static List<FruitTransaction> EMPTY_FRUIT_TRANSACTION_LIST;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static FruitTransactionService fruitTransactionService;
    private static OperationStrategy operationStrategy;
    private Storage storage;

    @BeforeAll
    public static void beforeAll() {
        EMPTY_FRUIT_TRANSACTION_LIST = new ArrayList<>();
        FRUIT_TRANSACTION_LIST = new ArrayList<>();
        FRUIT_TRANSACTION_LIST.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100));
        FRUIT_TRANSACTION_LIST.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 30));
        FRUIT_TRANSACTION_LIST.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 10));
        FRUIT_TRANSACTION_LIST.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10));
        FRUIT_TRANSACTION_LIST.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 20));
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void parseFruitData_normalDataParse_ok() {
        List<FruitTransaction> transactionList = fruitTransactionService.parseFruitData(FRUIT_DATA);
        assertEquals(FRUIT_TRANSACTION_LIST.size(), transactionList.size());
        assertEquals(FRUIT_TRANSACTION_LIST, transactionList);
    }

    @Test
    public void parseFruitData_nullAndEmptyData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionService.parseFruitData(EMPTY_FRUIT_DATA);
        });
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionService.parseFruitData(NULL_FRUIT_DATA);
        });
    }

    @Test
    public void processTransactionList_normalListProcess_ok() {
        fruitTransactionService.processTransactionList(FRUIT_TRANSACTION_LIST);
        assertEquals(EXPECTED_STORAGE_SIZE, Storage.fruits.size());
        assertEquals(BANANA_AMOUNT, Storage.fruits.get(FRUIT_TRANSACTION_LIST
                .get(FIRST_ELEMENT).getFruit()));
        assertEquals(APPLE_AMOUNT, Storage.fruits.get(FRUIT_TRANSACTION_LIST
                .get(SECOND_ELEMENT).getFruit()));
    }

    @Test
    public void processTransactionList_emptyListProcess_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionService.processTransactionList(EMPTY_FRUIT_TRANSACTION_LIST);
        });
    }
}
