package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import java.util.Map;
import java.util.Optional;

public class FruitServiceImpl implements FruitService {
    private Storage storage;

    public FruitServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public FruitTransaction add(FruitTransaction fruitTransaction) {
        Integer quantity = storage.getQuantityByFruit(fruitTransaction.fruit());

        if (fruitTransaction.quantity() <= 0) {
            throw new RuntimeException("Enter valid quantity!");
        }

        if (!storage.getAll().containsKey(fruitTransaction.fruit()) && (fruitTransaction.operation() == FruitTransaction.Operation.RETURN || fruitTransaction.operation() == FruitTransaction.Operation.SUPPLY)) {
            throw new RuntimeException("Enter valid fruit name!");
        }

        Optional.ofNullable(quantity)
                .ifPresentOrElse(
                        (currentQuantity) -> storage.add(
                                fruitTransaction.fruit(),
                                currentQuantity + fruitTransaction.quantity()
                        ),
                        () -> storage.add(fruitTransaction.fruit(), fruitTransaction.quantity())
                );
        return fruitTransaction;
    }

    @Override
    public FruitTransaction remove(FruitTransaction fruitTransaction) {
        Optional<Integer> quantityOptional = Optional.ofNullable(
                storage.getQuantityByFruit(fruitTransaction.fruit())
        );

        if (quantityOptional.isEmpty()) {
            throw new RuntimeException("Such fruit(" + fruitTransaction.fruit()
                    + ") is not in storage");
        }

        if (quantityOptional.get() - fruitTransaction.quantity() < 0) {
            throw new RuntimeException("There are not enough " + fruitTransaction.fruit() + "s in storage!");
        }

        storage.add(
                fruitTransaction.fruit(),
                quantityOptional.get() - fruitTransaction.quantity()
        );

        return fruitTransaction;
    }

    @Override
    public Map<String, Integer> getAll() {
        return storage.getAll();
    }
}
