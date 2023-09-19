package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FormaterService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import core.basesyntax.service.impl.FormaterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String PATH_TO_INPUT_FILE = "src/main/java/resources/input.csv";
    public static final String PATH_TO_REPORT_FILE = "src/main/java/resources/report.csv";

    public static void main(String[] args) {
        ReaderService readerService = new CsvReaderServiceImpl();
        FormaterService formaterService = new FormaterServiceImpl();
        FruitServiceImpl fruitStorageService = new FruitServiceImpl(createOperationMap());
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        List<String> inputFromFile = readerService.read(PATH_TO_INPUT_FILE);
        List<FruitTransaction> fruitTransactions = formaterService.form(inputFromFile);
        fruitStorageService.manageTransactions(fruitTransactions);
        String report = reportService.generateReport();
        writerService.writeToFile(report, PATH_TO_REPORT_FILE);
    }

    public static Map<Operation, OperationHandler> createOperationMap() {
        Map<Operation, OperationHandler> newMap = new HashMap<>();
        newMap.put(Operation.BALANCE, new BalanceOperationHandler());
        newMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        newMap.put(Operation.RETURN, new ReturnOperationHandler());
        newMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        return newMap;
    }
}
