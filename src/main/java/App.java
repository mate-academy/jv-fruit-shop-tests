import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitService;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.OperationHandlerImpl;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import core.basesyntax.service.nio.FileReaderService;
import core.basesyntax.service.nio.FileReaderServiceImpl;
import core.basesyntax.service.nio.FileWriterService;
import core.basesyntax.service.nio.FileWriterServiceImpl;
import core.basesyntax.service.parser.TransactionParser;
import core.basesyntax.service.parser.TransactionParserImpl;
import core.basesyntax.service.report.ReportService;
import core.basesyntax.service.report.ReportServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final String INPUT_FILE = "src/main/resources/fruit.csv";
    private static final String OUTPUT_FILE = "src/main/resources/resultFruits.csv";

    public static void main(String[] args) {
        FileReaderService reader = new FileReaderServiceImpl();

        List<String> lines = reader.read(INPUT_FILE);
        if (lines.isEmpty()) {
            throw new RuntimeException("Input file is empty");
        }

        TransactionParser parser = new TransactionParserImpl();
        FruitsDao fruitsDao = new FruitsDaoImpl();

        Map<FruitTransaction.Operation, OperationHandlers> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new OperationHandlerImpl());
        handlers.put(FruitTransaction.Operation.SUPPLY, new OperationHandlerImpl());
        handlers.put(FruitTransaction.Operation.PURCHASE, new OperationHandlerImpl());
        handlers.put(FruitTransaction.Operation.RETURN, new OperationHandlerImpl());

        HandlerService handlerService = new HandlerServiceImpl(handlers);
        FruitService fruitService = new FruitsServiceImpl(fruitsDao, handlerService);
        ReportService reportService = new ReportServiceImpl(fruitsDao);

        List<FruitTransaction> transactions = parser.parse(lines.subList(1, lines.size()));

        fruitService.processTransactions(transactions);
        String report = reportService.reportAllFruits();
        FileWriterService writer = new FileWriterServiceImpl();
        writer.write(OUTPUT_FILE, List.of(report));
    }
}
