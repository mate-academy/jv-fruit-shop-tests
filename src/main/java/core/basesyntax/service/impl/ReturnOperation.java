package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentQuantity = Storage.quantities.get(transaction.getFruit());
        int newQuantity = currentQuantity - transaction.getQuantity();
        Storage.quantities.put(transaction.getFruit(), newQuantity);
        Storage.quantities
                .merge(transaction.getFruit(), transaction.getQuantity(), Integer::sum);
    }
}
