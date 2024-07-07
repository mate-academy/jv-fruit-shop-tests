package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private FruitServiceImpl fruitService;

    @BeforeEach
    void setup() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler()
        );
        fruitService = new FruitServiceImpl(operationHandlers);
        Storage.clearStorage();
    }

    @Test
    void applyTransaction_balanceOperation() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, 100);
        fruitService.applyTransaction(transaction);
        assertEquals(100, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void applyTransaction_supplyOperation() {
        Storage.addFruit(APPLE, 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, 30);
        fruitService.applyTransaction(transaction);
        assertEquals(80, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void applyTransaction_purchaseOperation() {
        Storage.addFruit(APPLE, 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 20);
        fruitService.applyTransaction(transaction);
        assertEquals(30, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void applyTransaction_purchaseOperation_insufficientStock() {
        Storage.addFruit(APPLE, 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 20);
        assertThrows(IllegalArgumentException.class, () ->
                fruitService.applyTransaction(transaction));
    }

    @Test
    void applyTransaction_returnOperation() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, APPLE, 20);
        fruitService.applyTransaction(transaction);
        assertEquals(20, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void applyTransactions_multipleOperations() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 20)
        );
        fruitService.applyTransactions(transactions);
        assertEquals(140, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void getReportData_emptyStorage() {
        Map<String, Integer> reportData = fruitService.getReportData();
        assertTrue(reportData.isEmpty());
    }

    @Test
    void getReportData_nonEmptyStorage() {
        Storage.addFruit(APPLE, 50);
        Storage.addFruit(BANANA, 30);
        Map<String, Integer> reportData = fruitService.getReportData();
        assertEquals(2, reportData.size());
        assertEquals(50, reportData.get(APPLE));
        assertEquals(30, reportData.get(BANANA));
    }
}
