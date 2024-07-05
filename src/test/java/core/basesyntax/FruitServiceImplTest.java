package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FruitServiceImplTest {

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
                FruitTransaction.Operation.BALANCE, "apple", 100);
        fruitService.applyTransaction(transaction);
        assertEquals(100, Storage.getFruitQuantity("apple"));
    }

    @Test
    void applyTransaction_supplyOperation() {
        Storage.addFruit("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 30);
        fruitService.applyTransaction(transaction);
        assertEquals(80, Storage.getFruitQuantity("apple"));
    }

    @Test
    void applyTransaction_purchaseOperation() {
        Storage.addFruit("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        fruitService.applyTransaction(transaction);
        assertEquals(30, Storage.getFruitQuantity("apple"));
    }

    @Test
    void applyTransaction_purchaseOperation_insufficientStock() {
        Storage.addFruit("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        assertThrows(IllegalArgumentException.class, () ->
                fruitService.applyTransaction(transaction));
    }

    @Test
    void applyTransaction_returnOperation() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
        fruitService.applyTransaction(transaction);
        assertEquals(20, Storage.getFruitQuantity("apple"));
    }

    @Test
    void applyTransactions_multipleOperations() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)
        );
        fruitService.applyTransactions(transactions);
        assertEquals(140, Storage.getFruitQuantity("apple"));
    }

    @Test
    void getReportData_emptyStorage() {
        Map<String, Integer> reportData = fruitService.getReportData();
        assertTrue(reportData.isEmpty());
    }

    @Test
    void getReportData_nonEmptyStorage() {
        Storage.addFruit("apple", 50);
        Storage.addFruit("banana", 30);
        Map<String, Integer> reportData = fruitService.getReportData();
        assertEquals(2, reportData.size());
        assertEquals(50, reportData.get("apple"));
        assertEquals(30, reportData.get("banana"));
    }
}
