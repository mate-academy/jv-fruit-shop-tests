package core.basesyntax.dao.validator;

public interface Validator {
    boolean fruitNameIsNotNull(String name);

    boolean amountIsNegative(int amount);
}
