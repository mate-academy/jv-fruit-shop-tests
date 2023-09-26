package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;

public class SupplyOperationsHandler implements OperationsHandler {
    @Override
    public void operation(FruitDto fruitDto) {
        checkValue(fruitDto);
        Integer fruitBalanceValue = Storage.STORAGE.get(fruitDto.getName());
        Storage.STORAGE.put(fruitDto.getName(), fruitBalanceValue + fruitDto.getAmount());
    }

    private void checkValue(FruitDto fruitDto) {
        if (fruitDto.getAmount() < 0) {
            throw new RuntimeException("Supply value can't be negative");
        }
    }
}
