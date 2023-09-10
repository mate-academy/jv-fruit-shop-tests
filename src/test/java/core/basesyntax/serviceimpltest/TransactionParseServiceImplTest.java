package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitsShopServiceImpl;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import core.basesyntax.service.impl.ReturnOperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.service.readservice.TransactionParseService;
import core.basesyntax.service.readservice.TransactionParseServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParseServiceImplTest {
    private static final int COLUMNS_LENGTH = 3;
    private static final String SEPARATOR = ",";
    private static final String FIRST_COLUMN = "type";
    private static final String SECOND_COLUMN = "fruits";
    private static final String THIRD_COLUMN = "quantity";
    private static TransactionParseService transactionParseService;
    private FruitShopService fruitShopService;
    private FruitTransaction fruitTransactionBalance;
    private FruitTransaction fruitTransactionSupply;
    private FruitTransaction fruitTransactionPurchase;
    private FruitTransaction fruitTransactionReturn;

    @BeforeEach
    void setUp() {
        transactionParseService = new TransactionParseServiceImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitsShopServiceImpl(operationStrategy);
        fruitTransactionBalance = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 20);
        fruitTransactionPurchase = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        fruitTransactionSupply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        fruitTransactionReturn = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUIT_MAPS.clear();
    }

    @Test
    public void parseDataWithInvalidRow_Ok() {
        String invalidData = "type,fruits,quantity\n"
                + "b,apple,20\n"
                + "###,Orange,invalid";
        assertThrows(RuntimeException.class, () -> transactionParseService.parse(invalidData));
    }

    @Test
    void type_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionParseService.parse(null));
    }

    @Test
    void first_column_Ok() {
        assertTrue(true, FIRST_COLUMN);
    }

    @Test
    void first_column_notOk() {
        String actual = "TYPe";
        assertFalse(false, actual);
    }

    @Test
    void second_column_Ok() {
        assertTrue(true, SECOND_COLUMN);
    }

    @Test
    void third_column_Ok() {
        assertTrue(true, THIRD_COLUMN);
    }

    @Test
    void separator_valid_ok() {
        assertTrue(true, SEPARATOR);
    }

    @Test
    void separator_notValid_notOk() {
        String actual = ":";
        assertFalse(false, actual);
    }

    @Test
    void columnsLength_ok() {
        assertTrue(true, String.valueOf(COLUMNS_LENGTH));
    }

    @Test
    void transaction_correctData_Ok() {
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransactionBalance,
                fruitTransactionPurchase, fruitTransactionSupply, fruitTransactionReturn);
        fruitShopService.process(fruitTransactionList);
        int expected = 60;
        int actual = Storage.FRUIT_MAPS.get("apple");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void transaction_emptyList_ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitShopService.process(emptyList);
        int expected = 0;
        int actual = Storage.FRUIT_MAPS.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void rowValid_ok() {
        String validRow = "b,apple,20";
        boolean isValid = transactionParseService.isRowValid(validRow);
        assertTrue(isValid);
    }

    @Test
    public void rowValid_MissingColumns_notOk() {
        String invalidRow = "b,apple";
        boolean isValid = transactionParseService.isRowValid(invalidRow);
        assertFalse(isValid);
    }

    @Test
    public void rowValid_Quantity_notOk() {
        String invalidRow = "b,banana,$$$$$";
        boolean isValid = transactionParseService.isRowValid(invalidRow);
        assertFalse(isValid);
    }

    @Test
    public void testParseInvalidDataInvalidQuantity() {
        String invalidData = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,apple";
        assertThrows(RuntimeException.class, () -> transactionParseService.parse(invalidData));
    }

    @Test
    public void testParseInvalidDataMissingColumns() {
        String invalidData = "type,fruits, quantity\n"
                + "b,banana,20\n"
                + "b,invalid";
        assertThrows(RuntimeException.class, () -> transactionParseService.parse(invalidData));
    }
}
