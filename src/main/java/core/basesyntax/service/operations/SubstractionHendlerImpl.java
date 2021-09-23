package core.basesyntax.service.operations;

import core.basesyntax.model.Storage;
import core.basesyntax.model.TransactionDto;

public class SubstractionHendlerImpl implements OperationHendler {

    @Override
    public void apply(TransactionDto fruitRecord) {
        String fruitName = fruitRecord.getFruit();
        int curAmount = Storage.FRUIT_COUNT.get(fruitName) == null
                ? 0 : Storage.FRUIT_COUNT.get(fruitName);
        int newAmount = curAmount - fruitRecord.getAmount();
        if (newAmount < 0) {
            throw new RuntimeException("Amount can't be less than zero");
        }
        Storage.FRUIT_COUNT.put(fruitName, newAmount);
    }
}
