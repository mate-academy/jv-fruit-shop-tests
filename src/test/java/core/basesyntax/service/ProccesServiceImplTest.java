package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ProccesServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaceOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProccesServiceImplTest {
    private static ProccessService proccessService;

    @BeforeAll
    static void beforeAll() {
        proccessService = new ProccesServiceImpl(Map.of(
                Operation.BALANCE,new BalanceOperation(),
                Operation.RETURN,new ReturnOperation(),
                Operation.SUPPLY,new SupplyOperation(),
                Operation.PURCHASE, new PurchaceOperation()
        ));
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void operationServiceProccess_empty_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        assertDoesNotThrow(() ->
                proccessService.proccessing(fruitTransactions));
    }

    @Test
    void operationServiceProccess_nonEmptyList_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.BALANCE, "apple", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 10)
        );
        assertDoesNotThrow(() -> proccessService.proccessing(transactions));
    }
}
