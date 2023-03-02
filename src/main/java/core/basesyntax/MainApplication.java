package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApplication {
    private static final String INPUT_FILE = "src/main/resources/input.txt";
    private static final String OUTPUT_FILE = "src/main/resources/output.txt";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        ReaderService readerService = new ReaderServiceImpl();
        TransactionParserService transactionParserService = new TransactionParserServiceImpl();
        FruitShopService fruitShopService = new FruitShopServiceImpl(
                new OperationStrategyImpl(operationHandlerMap)
        );
        ReportMakerService reportMakerService = new ReportMakerServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        List<FruitTransaction> parsed =
                transactionParserService.parse(readerService.readFrom(INPUT_FILE));
        parsed.forEach(i -> System.out.println("new FruitTransaction(" + i.getOperation() + ", " + i.getFruit() + ", " + i.getQuantity() + "),"));
        Map<String, Integer> preparedMap = fruitShopService.report(parsed);
        String preparedReport = reportMakerService.make(preparedMap);
        writerService.write(preparedReport, OUTPUT_FILE);
    }
}
