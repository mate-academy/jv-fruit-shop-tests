package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.TransactionProcessor;
import core.basesyntax.strategy.impl.BalanceCodeService;
import core.basesyntax.strategy.impl.PurchaseCodeService;
import core.basesyntax.strategy.impl.ReturnCodeService;
import core.basesyntax.strategy.impl.SupplyCodeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionServiceImplTest {
    private static Map<FruitTransaction.Operation, TransactionProcessor> codeServiceMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceCodeService(),
            FruitTransaction.Operation.PURCHASE, new PurchaseCodeService(),
            FruitTransaction.Operation.RETURN, new ReturnCodeService(),
            FruitTransaction.Operation.SUPPLY, new SupplyCodeService()
    );
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(codeServiceMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
    }

    @Test
    void processTransactions_correctData_ok() {
        List<FruitTransaction> listFruit = new ArrayList<>();
        listFruit.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        listFruit.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20));
        listFruit.add(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20));
        listFruit.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20));
        transactionService.processTransactions(listFruit);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 40);
        assertEquals(expected, Storage.storageFruits);
    }

    @AfterEach
    void tearDown() {
        Storage.storageFruits.clear();
    }
}
