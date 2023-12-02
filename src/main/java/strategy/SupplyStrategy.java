package strategy;

import db.FruitStorage;
import model.FruitTransaction;

public class SupplyStrategy implements TransactionStrategy {
    @Override
    public void apply(FruitStorage fruitStorage, FruitTransaction transaction) {
        fruitStorage.addQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
