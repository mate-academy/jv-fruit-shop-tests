package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exceptions.NegativeValueException;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.FruitService;
import java.util.Optional;

public class FruitServiceImpl implements FruitService {
    private final StorageDao storage;

    public FruitServiceImpl(StorageDao storage) {
        this.storage = storage;
    }

    @Override
    public Transaction add(Transaction transaction) {
        Integer currentAmount = storage.getCurrentAmount(transaction.getProductName());
        if (transaction.getAmount() < 0) {
            throw new NegativeValueException("Amount can't be negative! ");
        }
        Optional.ofNullable(currentAmount)
                .ifPresentOrElse(
                        amount -> storage.add(
                                transaction.getProductName(),
                                currentAmount + transaction.getAmount()
                        ),
                        () -> storage.add(transaction.getProductName(), transaction.getAmount())
                );
        return transaction;
    }

    @Override
    public Transaction remove(Transaction transaction) {
        if (transaction.getAmount() < 0) {
            throw new NegativeValueException("Amount can't be negative! ");
        }

        Optional<Integer> currentAmount = Optional.ofNullable(
                storage.getCurrentAmount(transaction.getProductName())
        );
        if (currentAmount.isEmpty()) {
            throw new RuntimeException("No such fruit in the Storage: "
                    + transaction.getProductName());
        }
        if (currentAmount.get() < transaction.getAmount()) {
            throw new NegativeValueException("Negative as a rest, "
                    + "current amount is less then removing!");
        }
        storage.add(transaction.getProductName(),
                currentAmount.get() - transaction.getAmount());
        return transaction;
    }
}
