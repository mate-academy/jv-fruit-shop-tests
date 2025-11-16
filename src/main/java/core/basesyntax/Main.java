package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/reportToRead.csv";
    private static final String OUTPUT_FILE = "src/main/resources/reportResult.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        Converter converter = new ConverterImpl();

        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(Operation.RETURN, new ReturnHandler());
        operationHandlers.put(Operation.SUPPLY, new SupplyHandler());

        OperationStrategy operationStrategy = new OperationStrategy(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        WriteService writeService = new WriteServiceImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        List<String> lines = readerService.read(INPUT_FILE);
        List<FruitTransaction> transactions = converter.convert(lines);
        shopService.process(transactions);
        String report = reportGenerator.getReport();
        writeService.write(report, OUTPUT_FILE);

        System.out.println("Report successfully generated: " + OUTPUT_FILE);
    }
}
