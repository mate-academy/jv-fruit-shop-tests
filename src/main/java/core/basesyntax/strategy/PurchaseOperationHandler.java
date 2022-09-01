package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(Transaction transaction) {
        Integer amountInShop = 0;
        if (Storage.storage.containsKey(transaction.getFruit())) {
            amountInShop = Storage.storage.get(transaction.getFruit());
        } else {
            throw new RuntimeException("fruit not exist in the shop");
        }
        amountInShop = amountInShop - transaction.getAmount();
        if (amountInShop >= 0) {
            Storage.storage.put(transaction.getFruit(), amountInShop);
        } else {
            throw new RuntimeException("amount of fruit in the shop can`t be lower than 0");
        }
    }
}
