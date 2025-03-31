package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitBalance;
import java.util.List;

public class FruitBalanceDaoImpl implements FruitBalanceDao {
    @Override
    public void add(FruitBalance fruitBalance) {
        Storage.fruitBalances.add(fruitBalance);
    }

    @Override
    public FruitBalance get(String fruit) {
        return Storage.fruitBalances.stream()
                .filter(fruitBalance -> fruitBalance.getFruit().equals(fruit))
                .findFirst().orElse(null);
    }

    @Override
    public List<FruitBalance> getFruitBalances() {
        return Storage.fruitBalances;
    }
}
