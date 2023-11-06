package core.basesyntax;

import core.basesyntax.service.DataHandlerService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.DataHandlerServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.ReportWriterServiceImpl;
import core.basesyntax.strategy.StorageUpdateHandler;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String REPORT_FILE_NAME = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        String inputData = fileReaderService.readInputData(INPUT_FILE_NAME);

        List<StorageUpdateHandler> storageUpdateHandlers = new ArrayList<>();
        storageUpdateHandlers.add(new FruitBalanceHandler());
        storageUpdateHandlers.add(new FruitSupplyHandler());
        storageUpdateHandlers.add(new FruitPurchaseHandler());
        storageUpdateHandlers.add(new FruitReturnHandler());

        DataHandlerService dataHandlerService = new DataHandlerServiceImpl(storageUpdateHandlers);
        String reportData = dataHandlerService.calculateInputData(inputData);

        ReportWriterService reportWriterService = new ReportWriterServiceImpl();
        reportWriterService.writeReport(reportData, REPORT_FILE_NAME);
    }
}
