package strategy;

import db.Storage;
import model.Fruit;
import model.TransactionDto;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(TransactionDto transactionDto) {
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        if (Storage.storage.get(fruit) != null) {
            int quantity = transactionDto.getQuantity();
            int oldQuantity = Storage.storage.get(fruit);
            int newQuality = oldQuantity + quantity;
            Storage.storage.put(fruit, newQuality);
        }
        Storage.storage.put(fruit, transactionDto.getQuantity());

    }
}
