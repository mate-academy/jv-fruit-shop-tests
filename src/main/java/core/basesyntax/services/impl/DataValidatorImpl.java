package core.basesyntax.services.impl;

import core.basesyntax.exceptions.IncorrectAmountException;
import core.basesyntax.services.DataValidator;
import java.util.List;

public class DataValidatorImpl implements DataValidator {
    @Override
    public void validateAmount(int balance, int purchaseAmount) {
        positiveAmountValidator(purchaseAmount);
        if (balance < purchaseAmount) {
            throw new IncorrectAmountException("Purchase amount: " + purchaseAmount
                    + " is greater than available balance: " + balance);
        }
    }

    @Override
    public void positiveAmountValidator(Integer fruitAmount) {
        if (fruitAmount < 0) {
            throw new IncorrectAmountException("Mistake, purchase amount can't be negative!");
        }
    }

    @Override
    public boolean checkListNotEmpty(List<String> records) {
        if (!records.isEmpty()) {
            return true;
        }
        throw new RuntimeException("Invalid input information: empty records");
    }
}
