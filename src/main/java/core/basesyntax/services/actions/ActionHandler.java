package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;

public interface ActionHandler {
    boolean executeAction(String nameOfGoods, Integer valueOfTask);

    default void validateData(Storage fruitDB, String nameOfGoods) {
        if (fruitDB == null) {
            throw new ValidationDataException("ActionHandle error db is null");
        }
        if (fruitDB.getStorageFruits().isEmpty()) {
            throw new ValidationDataException("ActionHandle error db is empty");
        }
        if (fruitDB.getStorageFruits().get(nameOfGoods) == null) {
            throw new ValidationDataException("Client can't buy not existing product!");
        }
    }

    default void validateInfo(String nameOfGood, Integer valueOfTask) {
        if (nameOfGood == null) {
            throw new ValidationDataException("ActionHandler error! NameOfGood can't be null!");
        }
        if (nameOfGood.isEmpty()) {
            throw new ValidationDataException("ActionHandler error! NameOfGood can't be empty!");
        }
        if (valueOfTask == null) {
            throw new ValidationDataException("ActionHandler error! valueOfTask can't be null!");
        }
        if (valueOfTask < 0) {
            throw new ValidationDataException("ActionHandler error! "
                    + "valueOfTask can't be negative!");
        }
    }
}
