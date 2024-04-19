package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.TransactionProcessorServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.ReturnStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final String input_File_Path = "src/main/resources/fruitList.csv";
        final String output_File_Path = "src/main/resources/output.csv";

        Map<FruitTransaction.Operation, OperationStrategy> strategyMap
                = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
                FruitTransaction.Operation.SUPPLY, new SupplyStrategy(),
                FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
                FruitTransaction.Operation.RETURN, new ReturnStrategy()
        );

        ReaderService readerService = new ReaderServiceImpl();
        ParseService parseService = new ParseServiceImpl();
        TransactionProcessorServiceImpl processorService =
                new TransactionProcessorServiceImpl(strategyMap);
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();

        List<String> fileContent =
                readerService.readFromFilesContents(input_File_Path);

        List<FruitTransaction> parsedFromString =
                parseService.parseFromString(fileContent);

        Map<String, Integer> fruitCounts = processorService.processTransaction(parsedFromString);

        List<String> report = reportService.generateReport(fruitCounts);

        writerService.writeToFile(output_File_Path, report);
    }
}
