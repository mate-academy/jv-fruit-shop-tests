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

    @AfterEach
    void tearDown() {
        Storage.DB.clear();
    }
}
