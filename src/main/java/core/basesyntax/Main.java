package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.service.operation.OperationHandlerMap;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.List;

public class Main {
    private static final String toFileName = "src/main/resources/testRecord.csv";
    private static final String fromFileName = "src/main/resources/testData.csv";

    public static void main(String[] args) {
        Strategy strategy = new StrategyImpl(OperationHandlerMap.operationHandlerMap);
        Reader reader = new ReaderImpl();
        List<String> dataFromFile = reader.read(fromFileName);
        Parser parser = new ParserImpl();
        List<FruitTransaction> fruitTransactionList = parser.parse(dataFromFile);
        FruitService processor = new FruitServiceImpl(strategy);
        processor.process(fruitTransactionList);
        Writer writer = new WriterImpl(Storage.fruitMap);
        writer.write(toFileName);
    }
}
