package core.basesyntax.basesyntax;

import core.basesyntax.basesyntax.dao.FruitDao;
import core.basesyntax.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.FileService;
import core.basesyntax.basesyntax.service.FileServiceImpl;
import core.basesyntax.basesyntax.service.FruitService;
import core.basesyntax.basesyntax.service.FruitServiceImpl;
import core.basesyntax.basesyntax.service.operations.BalanceOperationsHandler;
import core.basesyntax.basesyntax.service.operations.PurchaseOperationsHandler;
import core.basesyntax.basesyntax.service.operations.SupplyOperationsHandler;
import core.basesyntax.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.basesyntax.service.strategy.OperationsStrategy;
import core.basesyntax.basesyntax.service.strategy.OperationsStrategyImpl;
import core.basesyntax.basesyntax.service.strategy.ReturnOperationHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "input.csv";
    private static final String REPORT_FILE = "report.csv";

    public static void main(String[] args) {
        Map<Operations, OperationHandler> operationsHandlerMap = Map.of(
                Operations.BALANCE, new BalanceOperationsHandler(),
                Operations.SUPPLY, new SupplyOperationsHandler(),
                Operations.PURCHASE, new PurchaseOperationsHandler(),
                Operations.RETURN, new ReturnOperationHandler()
        );

        FruitDao fruitDao = new FruitDaoImpl();
        OperationsStrategy strategy = new OperationsStrategyImpl(operationsHandlerMap);
        FruitService fruitService = new FruitServiceImpl(fruitDao, strategy);
        FileService fileService = new FileServiceImpl();

        List<String> fileRows = fileService.readFile(INPUT_FILE);
        fruitService.addFruitFromList(fileRows);
        fileService.writeToFile(REPORT_FILE);
    }
}

