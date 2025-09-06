package core.basesyntax.reportgenerator;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransactionhandler.BalanceHandler;
import core.basesyntax.fruittransactionhandler.OperationHandler;
import core.basesyntax.fruittransactionhandler.PurchaseHandler;
import core.basesyntax.fruittransactionhandler.ReturnHandler;
import core.basesyntax.fruittransactionhandler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import core.basesyntax.operationstrategy.OperationStrategyImpl;
import core.basesyntax.operationstrategy.OperationStrategyService;
import core.basesyntax.shopservice.ShopService;
import core.basesyntax.shopservice.ShopServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.basesyntax.model.TypeOfTransaction.B;
import static core.basesyntax.model.TypeOfTransaction.R;
import static core.basesyntax.model.TypeOfTransaction.S;

class ReportGeneratorImplTest {

    private static final Storage storage = new Storage();
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    public void afterEach() {

        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }

    @Test
    void getReport_dataToReportIsNull_notOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        Assertions.assertThrows(RuntimeException.class, () -> reportGenerator.getReport(null));
    }

    @Test
    void getReport_generateReport_ok() {
        storage.getDataStorageMapOrDefault("orange").put("orange",20);
        storage.getDataStorageMapOrDefault("banana").put("banana",10);
        storage.getDataStorageMapOrDefault("apple").put("apple",50);
        String result = "fruit,quantity" + System.lineSeparator() +
                "orange,20" + System.lineSeparator() +
                "banana,10" + System.lineSeparator() +
                "apple,50" + System.lineSeparator();
        Assertions.assertEquals(result, reportGenerator
                .getReport(storage));
    }

    @Test
    void getReport_isOverHeadPresent_ok() {
        storage.getDataStorageMapOrDefault("banana").put("banana",10);
        String report = reportGenerator.getReport(storage);
        List<String> listFromReport = report.lines().toList();
        Assertions.assertEquals("fruit,quantity",
                listFromReport.get(0));
    }
}
