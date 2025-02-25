package core.basesyntax;

import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.impl.CsvReaderImpl;
import core.basesyntax.impl.CsvWriterImpl;
import core.basesyntax.impl.OperationStrategyImpl;
import core.basesyntax.impl.ProcessDataImpl;
import core.basesyntax.impl.ReportCreatorImpl;
import core.basesyntax.impl.ShopUpdateImpl;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.CsvFileWriter;
import core.basesyntax.service.ProcessData;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ShopUpdateService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.Operation;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Map<Operation, OperationHandler> MAP = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.SUPPLY, new SupplyHandler());
    private static final String FILE_FROM_PATH = "src/main/java/resources/InputCSV.csv";
    private static final String FILE_TO_PATH = "src/main/java/resources/OutputCSV.csv";

    public static void main(String[] args) {
        CsvFileReader csvFileReader = new CsvReaderImpl();
        ProcessData processData = new ProcessDataImpl();
        Strategy operationStrategy = new OperationStrategyImpl(MAP);
        ShopUpdateService fruitShopUpdateService
                = new ShopUpdateImpl(operationStrategy);
        ReportCreator createReport = new ReportCreatorImpl();
        CsvFileWriter csvFileWriter = new CsvWriterImpl();
        List<String> fileLines = csvFileReader.readFile(FILE_FROM_PATH);
        fruitShopUpdateService.update(processData.process(fileLines));
        csvFileWriter.writeFile(FILE_TO_PATH, createReport.createReport());
    }
}
