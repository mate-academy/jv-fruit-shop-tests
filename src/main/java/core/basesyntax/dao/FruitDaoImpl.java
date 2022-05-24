package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {

    @Override
    public void add(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("FruitTransaction can not be null");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit can not be null");
        }
        if (!fruitTransaction.getFruit().chars().allMatch(Character::isLetter)) {
            throw new RuntimeException("Fruit name must contain letters only");
        }
        if (fruitTransaction.getOperation() == null) {
            throw new RuntimeException("Fruit operation can not be null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit quantity can not be less than 0");
        }
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction);
    }

    @Override
    public FruitTransaction get(String fruitName) {
        if (fruitName == null) {
            throw new RuntimeException("Fruit name can not be null");
        }
        if (!fruitName.chars().allMatch(Character::isLetter)) {
            throw new RuntimeException("Fruit name must contain letters only");
        }
        return Storage.fruits.get(fruitName);
    }

    @Override
    public List<FruitTransaction> getAll() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (Map.Entry<String, FruitTransaction> entry : Storage.fruits.entrySet()) {
            fruitTransactionList.add(entry.getValue());
        }
        return fruitTransactionList;
    }
}
