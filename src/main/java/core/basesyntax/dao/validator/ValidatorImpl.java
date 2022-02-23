package core.basesyntax.dao.validator;

public class ValidatorImpl implements Validator {
    @Override
    public boolean amountIsNegative(int amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount is negative " + amount);
        }
        return true;
    }

    @Override
    public boolean fruitNameIsNotNull(String name) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("There is no fruit to find in map");
        }
        return true;
    }

    protected boolean validate(String name, int amount) {
        return amountIsNegative(amount) && fruitNameIsNotNull(name);
    }
}
