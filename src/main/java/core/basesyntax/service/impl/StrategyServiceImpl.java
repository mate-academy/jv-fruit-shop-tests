package core.basesyntax.service.impl;

import core.basesyntax.model.CsvLineDto;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyService;
import core.basesyntax.strategy.FruitWorkStrategy;
import java.util.List;

public class StrategyServiceImpl implements StrategyService {
    @Override
    public void applyStrategy(List<CsvLineDto> fileData, FruitWorkStrategy strategy) {
        for (CsvLineDto dataLine : fileData) {
            Operation key = Operation.getKey(dataLine.getOperation());
            String fruitName = dataLine.getFruitName();
            int fruitNumber = Integer.parseInt(dataLine.getNumber());
            strategy.get(key).apply(fruitNumber, fruitName);
        }
    }
}
