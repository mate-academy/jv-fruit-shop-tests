package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;

public class PurchaseOperationsHandler implements OperationsHandler {
    @Override
    public void operation(FruitDto fruitDto) {
        checkValue(fruitDto);
        Integer fruitBalanceValue = Storage.STORAGE.get(fruitDto.getName());
        checkBalance(fruitDto, fruitBalanceValue);
        Storage.STORAGE.put(fruitDto.getName(), fruitBalanceValue - fruitDto.getAmount());
    }

    private void checkBalance(FruitDto fruitDto, int storageFruitAmount) {
        if (fruitDto.getAmount() > storageFruitAmount) {
            throw new RuntimeException("Purchase value isn't correct. "
                                        + "Storage haven't such amount of "
                                        + fruitDto.getName());
        }
    }

    private void checkValue(FruitDto fruitDto) {
        if (fruitDto.getAmount() < 0) {
            throw new RuntimeException("Purchase value can't be negative");
        }
    }
}
