package core.basesyntax;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.type.service.BalanceHandler;
import core.basesyntax.service.type.service.OperationHandler;
import core.basesyntax.service.type.service.PurchaseHandler;
import core.basesyntax.service.type.service.ReturnHandler;
import core.basesyntax.service.type.service.SupplyHandler;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler = new HashMap<>();
        mapTypeHandler.put(FruitRecordDto.Activities.BALANCE, new BalanceHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.PURCHASE, new PurchaseHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.RETURN, new ReturnHandler());
        mapTypeHandler.put(FruitRecordDto.Activities.SUPPLY, new SupplyHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(mapTypeHandler);
        String pathFrom = "src/main/resources/filesFruitShop.csv";
        String pathTo = "src/main/resources/report_fruit_shop.csv";
        ReportService reportService = new ReportServiceImpl(operationStrategy);
        reportService.getReport(pathFrom, pathTo);
    }
}
