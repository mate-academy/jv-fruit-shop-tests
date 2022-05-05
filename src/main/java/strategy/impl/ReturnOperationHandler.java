package strategy.impl;

import db.Storage;
import model.Fruit;
import strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void process(Fruit fruit, Integer quantity) {
        Integer initialQuantity = Storage.storage.get(fruit);
        Storage.storage.put(fruit,
                initialQuantity == null
                        ? quantity
                        : initialQuantity + quantity);
    }
}
