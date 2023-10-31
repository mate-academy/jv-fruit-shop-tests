package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.operationhadler.BalanceHandler;
import core.basesyntax.service.impl.operationhadler.PurchaseHandler;
import core.basesyntax.service.impl.operationhadler.ReturnHandler;
import core.basesyntax.service.impl.operationhadler.SupplyHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String EXPECT =
            "fruit,quantity\n" + "banana,152" + System.lineSeparator()
                    + "apple," + "90" + System.lineSeparator();
    private List<String> expected;
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategy;
    private ParserServiceImpl fileParser;
    private ReportServiceImpl reportService;
    private FruitShopService fruitShop;

    @BeforeEach
    public void setUp() {
        fileParser = new ParserServiceImpl();
        reportService = new ReportServiceImpl();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        operationStrategy = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());
        OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl(operationStrategy);
        fruitShop = new FruitShopService(operationStrategyImpl);
    }

    @Test
    public void reportPreparation_correctStringRecords_ok() {
        List<FruitTransaction> transactions = fileParser.parseData(expected);
        fruitShop.processOfOperations(transactions);
        String actual = reportService.reportPreparation();
        assertEquals(EXPECT, actual);
    }
}
