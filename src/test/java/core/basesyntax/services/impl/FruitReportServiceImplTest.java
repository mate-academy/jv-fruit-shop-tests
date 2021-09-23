package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.services.FruitReportService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitReportServiceImplTest {
    private FruitReportService fruitReportService;
    private Map<Transaction.Operation, Operation> operationMap;

    @Before
    public void setUp() {
        fruitReportService = new FruitReportServiceImpl();
        operationMap = new HashMap<>();
        operationMap.put(Transaction.Operation.RETURN, new SupplyOperation());
        operationMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        operationMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        operationMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("apple", 100);
    }

    @Test
    public void createReport_Ok() {
        List<String> listData = new ArrayList<>();
        listData.add("b,banana,20");
        listData.add("b,apple,100");
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        assertEquals(expected, fruitReportService.getFormattedStringReport());
        assertEquals(expected, fruitReportService.createReport(listData, operationMap));
    }
}
