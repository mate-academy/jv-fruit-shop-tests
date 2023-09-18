package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private TransactionService transactionService;
    private FruitStrategy fruitStrategy;

    @BeforeEach
    void setUp() {
        fruitStrategy = new FruitStrategy(Map.of(
                FruitTransaction.Operation.BALANCE,new BalanceOperationHandler(),
                FruitTransaction.Operation.SUPPLY,new SupplyReturnOperationHandler(),
                FruitTransaction.Operation.PURCHASE,new PurchaseOperationHandler()));
        transactionService = new TransactionServiceImpl(fruitStrategy);
    }

    @Test
    void processData_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",20));
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple",5));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",10));
        transactionService.processData(list);
        Assertions.assertEquals(15,Storage.DB.get("apple"));
    }

    @Test
    void fruitTransaction_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE,"apple",20);
        FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "apple",5);
        FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "banana",20);
        transactionService.processData(List.of(fruitTransaction,fruitTransaction1,
                fruitTransaction2));
        Assertions.assertEquals(25,Storage.DB.get("apple"));
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setQuantity(10);
        fruitTransaction2.setFruit("banana");
        transactionService.processData(List.of(fruitTransaction,fruitTransaction1,
                fruitTransaction2));
        Assertions.assertEquals(10,Storage.DB.get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.DB.clear();
    }
}
