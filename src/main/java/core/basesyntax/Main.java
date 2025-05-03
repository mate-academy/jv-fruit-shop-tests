package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadFileService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.OperationHandlerStrategyImpl;
import core.basesyntax.service.impl.OperationServiceImpl;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReadFileServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriteFileServiceImpl;
import java.util.List;

public class Main {
    private static final String FROM_FILE = "src/main/java/resources/input.csv";
    private static final String TO_FILE = "src/main/java/resources/report.csv";

    public static void main(String[] args) {
        ReadFileService readService = new ReadFileServiceImpl();
        List<String> readFile = readService.read(FROM_FILE);
        ParseService parseService = new ParseServiceImpl();
        List<FruitTransaction> infoFromFile = parseService.getInfo(readFile);
        StorageDao storageDao = new StorageDaoImpl();
        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl();
        OperationService operationService = new OperationServiceImpl(operationHandlerStrategy);
        operationService.calculate(infoFromFile);
        ReportService reportService = new ReportServiceImpl(storageDao);
        String reportedInformation = reportService.report();
        new WriteFileServiceImpl().write(reportedInformation, TO_FILE);
    }
}
