package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Storage;
import core.basesyntax.model.TypeOperations;
import core.basesyntax.service.oparation.BalanceHandler;
import core.basesyntax.service.oparation.OperationHandler;
import core.basesyntax.service.oparation.PurchaseHandler;
import core.basesyntax.service.oparation.ReturnHandler;
import core.basesyntax.service.oparation.SupplyHandler;
import core.basesyntax.service.reader.ReaderService;
import core.basesyntax.service.reader.ReaderServiceImpl;
import core.basesyntax.service.report.FruitRecordDtoParser;
import core.basesyntax.service.report.FruitRecordDtoParserImpl;
import core.basesyntax.service.reporter.ReportGeneratorImpl;
import core.basesyntax.service.writer.ReportWriter;
import core.basesyntax.service.writer.ReportWriterImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_CSV = "src/main/resources/inputFile.csv";
    private static final String REPORT_FILE_CSV = "src/main/resources/reportFile.csv";

    public static void main(String[] args) {
        writeReportToFile();
    }

    private static void writeReportToFile() {
        Map<String, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(TypeOperations.BALANCE.get(), new BalanceHandler());
        operationHandlersMap.put(TypeOperations.SUPPLY.get(), new SupplyHandler());
        operationHandlersMap.put(TypeOperations.PURCHASE.get(), new PurchaseHandler());
        operationHandlersMap.put(TypeOperations.RETURN.get(), new ReturnHandler());
        Storage.getFruitStorageMap().put(new Fruit("banana"), 0);
        Storage.getFruitStorageMap().put(new Fruit("apple"), 0);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> readFromInputFile = readerService.read(INPUT_FILE_CSV);
        FruitRecordDtoParser fruitRecordDtoParser =
                new FruitRecordDtoParserImpl(operationHandlersMap);
        fruitRecordDtoParser.createFruitRecordDto(readFromInputFile);
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String reportString = reportGenerator.createReport(Storage.getFruitStorageMap());
        ReportWriter reportWriter = new ReportWriterImpl();
        reportWriter.write(REPORT_FILE_CSV, reportString);
    }
}
