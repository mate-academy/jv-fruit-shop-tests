package core.basesyntax.dao;

import core.basesyntax.database.Storage;

public class FruitTransactionDaoImpl implements FruitTransactionDao {
    private static final String REQUIRED_LETTERS_PATTERN = "[a-z]+";
    private static final int MINIMUM_REQUIRED_LENGTH = 3;

    @Override
    public void addToStorage(String fruitName,Integer fruitQuantity) {
        checkIfNull(fruitName);
        checkLengthOfName(fruitName);
        checkIfLetters(fruitName);
        Storage.fruitTransactionStorage.put(fruitName,fruitQuantity);
    }

    @Override
    public Integer getFromStorage(String fruitName) {
        checkIfNull(fruitName);
        checkIfLetters(fruitName);
        return Storage.fruitTransactionStorage.getOrDefault(fruitName, 0);
    }

    private void checkIfLetters(String fruitName) {
        if (!(fruitName.matches(REQUIRED_LETTERS_PATTERN))) {
            throw new RuntimeException("This fruit name " + fruitName
            + " is not suitable to pattern" + REQUIRED_LETTERS_PATTERN);
        }
    }

    private void checkIfNull(String fruitName) {
        if (fruitName == null) {
            throw new RuntimeException("The fruit name cannot be null");
        }
    }

    private void checkLengthOfName(String fruitName) {
        if (fruitName.length() < MINIMUM_REQUIRED_LENGTH) {
            throw new RuntimeException("The fruit name length is too short " + fruitName.length()
            + " must be " + MINIMUM_REQUIRED_LENGTH);
        }
    }
}
