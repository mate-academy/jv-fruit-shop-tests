package core.basesyntax.service;

import core.basesyntax.model.FruitDto;
import core.basesyntax.service.strategy.Strategy;
import java.util.List;

public class CreateReportText {
    private final Strategy strategy = new Strategy();

    public void generateReport(List<FruitDto> fruitDtos) {
        for (FruitDto f: fruitDtos) {
            if (f.getFruitName().isEmpty() || f.getOperationType().isEmpty()
                    || f.getNumberOfFruit() < 0) {
                throw new RuntimeException("Corrupted data!!");
            }
            strategy.getOperationHendler(f.getOperationType())
                    .operation(f.getFruitName(), f.getNumberOfFruit());
        }
    }
}
