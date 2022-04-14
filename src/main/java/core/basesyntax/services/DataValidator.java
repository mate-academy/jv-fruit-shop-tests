package core.basesyntax.services;

import java.util.List;

public interface DataValidator {
    void validateAmount(int balance, int amount);

    void positiveAmountValidator(Integer fruitAmount);

    boolean checkListNotEmpty(List<String> records);
}
