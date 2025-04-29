package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    @Test
    public void getReportOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);
        ShopService service = new ShopServiceImpl(strategy);
        service.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,7\napple,110";

        assertEquals(actual, expected);
    }
}
