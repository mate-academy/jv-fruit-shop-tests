package core.basesyntax.service.implementation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FruitServiceImpl implements FruitService {

    private final OperationStrategy operationStrategy;

    @Override
    public void processTransactions(List<FruitTransaction> fruitTransactions) {
        fruitTransactions
                .forEach(line ->
                        operationStrategy.get(line.getOperation()).processTransaction(line));
    }
}
