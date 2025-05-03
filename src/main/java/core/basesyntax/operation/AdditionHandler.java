package core.basesyntax.operation;

import core.basesyntax.database.Database;
import core.basesyntax.model.Record;

public class AdditionHandler implements OperationHandler {
    @Override
    public void apply(Record record) {
        String fruit = record.getFruit();
        int currentAmount = Database.FRUIT_BALANCE.get(fruit) == null
                ? 0 : Database.FRUIT_BALANCE.get(fruit);
        int newAmount = currentAmount + record.getAmount();
        Database.FRUIT_BALANCE.put(fruit, newAmount);
    }
}
