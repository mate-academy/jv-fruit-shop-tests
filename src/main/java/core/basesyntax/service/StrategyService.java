package core.basesyntax.service;

import core.basesyntax.model.CsvLineDto;
import core.basesyntax.strategy.FruitWorkStrategy;
import java.util.List;

public interface StrategyService {
    void applyStrategy(List<CsvLineDto> fileData, FruitWorkStrategy fruitWork);
}
