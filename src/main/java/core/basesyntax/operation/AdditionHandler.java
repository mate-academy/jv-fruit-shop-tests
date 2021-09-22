package core.basesyntax.operation;

import core.basesyntax.model.Record;
import core.basesyntax.report.FruitBalance;

public class AdditionHandler implements OperationHandler {
    @Override
    public void apply(Record record) {
        String fruit = record.getFruit();
        int currentAmount = FruitBalance.FRUIT_BALANCE.get(fruit) == null
                ? 0 : FruitBalance.FRUIT_BALANCE.get(fruit);
        int newAmount = currentAmount + record.getAmount();
        FruitBalance.FRUIT_BALANCE.put(fruit, newAmount);
    }
}
