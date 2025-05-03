package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final List<String> ONLY_HEADER_REPORT = new ArrayList<>(
            List.of("fruit,quantity"));
    private static final List<String> FULL_REPORT = new ArrayList<>(
            List.of("fruit,quantity",
                    "apple,90"));
    private static List<FruitTransaction> fruitTransactions;
    private static ReportService reportService;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setup() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        reportService = new ReportServiceImpl();
        fruitTransactions = new ArrayList<>();
        FruitTransaction balanced = new FruitTransaction();
        balanced.setOperation(FruitTransaction.Operation.BALANCE);
        balanced.setFruit("apple");
        balanced.setQuantity(100);
        FruitTransaction returned = new FruitTransaction();
        returned.setOperation(FruitTransaction.Operation.RETURN);
        returned.setFruit("apple");
        returned.setQuantity(10);
        FruitTransaction purchased = new FruitTransaction();
        purchased.setOperation(FruitTransaction.Operation.PURCHASE);
        purchased.setFruit("apple");
        purchased.setQuantity(20);
        FruitTransaction supplyed = new FruitTransaction();
        supplyed.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyed.setFruit("apple");
        supplyed.setQuantity(0);
        fruitTransactions.add(balanced);
        fruitTransactions.add(returned);
        fruitTransactions.add(purchased);
        fruitTransactions.add(supplyed);
    }

    @Test
    public void create_emptyFruitTransactions_ok() {
        assertEquals(ONLY_HEADER_REPORT, reportService.create(Collections
                .emptyList(), operationStrategy));
    }

    @Test
    public void create_fullFruitTransactions_ok() {
        assertEquals(FULL_REPORT, reportService.create(fruitTransactions, operationStrategy));
    }
}
