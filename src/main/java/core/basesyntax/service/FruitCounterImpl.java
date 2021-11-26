package core.basesyntax.service;

import core.basesyntax.model.ParsedLine;
import java.util.List;
import service.FruitCounter;
import strategy.OperationStrategy;
import strategy.implement.OperationStrategyImpl;

public class FruitCounterImpl implements FruitCounter {
    @Override
    public boolean fruitCounter(List<ParsedLine> parsedLineList) {
        OperationStrategy strategy = new OperationStrategyImpl();
        for (ParsedLine line: parsedLineList) {
            strategy.getOperationService(line).operation(line);
        }
        return true;
    }
}
