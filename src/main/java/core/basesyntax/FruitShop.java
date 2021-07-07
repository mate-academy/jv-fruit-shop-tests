package core.basesyntax;

import core.basesyntax.dto.Transaction;
import core.basesyntax.handler.impl.OperationHandler;
import core.basesyntax.handler.impl.impl.AddOperationHandler;
import core.basesyntax.handler.impl.impl.BalanceOperationHandler;
import core.basesyntax.handler.impl.impl.RemoveOperationHandler;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.strategy.impl.OperationStrategy;
import core.basesyntax.strategy.impl.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShop {
    public static void main(String[] args) {
        Map<String, OperationHandler> handlers = new HashMap<>();
        handlers.put("b", new BalanceOperationHandler());
        handlers.put("s", new AddOperationHandler());
        handlers.put("r", new AddOperationHandler());
        handlers.put("p", new RemoveOperationHandler());

        FileReader fileReader = new FileReaderImpl();
        List<String> lines = fileReader.read("src/main/resources/input.csv");
        Parser parser = new ParserImpl(handlers.keySet());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        for (int i = 1; i < lines.size(); i++) {
            Transaction transaction = parser.parseLine(lines.get(i));
            OperationHandler handler = operationStrategy.get(transaction.getOperation());
            handler.apply(transaction);
        }

        FruitShopService fruitShopService = new FruitShopServiceImpl();
        String report = fruitShopService.createReport();

        FileWriter fruitFileWriter = new FileWriterImpl();
        fruitFileWriter.writeToFile(report, "src/main/resources/output.csv");

    }
}
