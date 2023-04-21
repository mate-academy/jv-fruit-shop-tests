package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

import static core.basesyntax.db.Storage.fruitData;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (fruitData.containsKey(fruitTransaction.getFruit())) {
            Integer fruitAmountInfo = fruitData.getOrDefault(fruitTransaction.getFruit(),
                    fruitTransaction.getAmount());
            fruitAmountInfo += fruitTransaction.getAmount();
            fruitData.replace(fruitTransaction.getFruit(),fruitAmountInfo);
        } else {
            fruitData.put(fruitTransaction.getFruit(),fruitTransaction.getAmount());
        }
    }
}
