package core.basesyntax.service;

import core.basesyntax.model.ParsedLine;
import java.util.List;
import service.FruitCounter;
import strategy.OperationStrategy;
import strategy.implement.OperationStrategyImpl;

public class FruitCounterImpl implements FruitCounter {
    OperationStrategy strategy;

    public FruitCounterImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean fruitCounter(List<ParsedLine> parsedLineList) {
        for (ParsedLine line: parsedLineList) {
            strategy.getOperationService(line).operation(line);
        }
        return true;
    }
}
