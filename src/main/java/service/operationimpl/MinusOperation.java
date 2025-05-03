package service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParsedLine;
import service.OperationService;

public class MinusOperation implements OperationService {
    @Override
    public boolean operation(ParsedLine line) {
        for (Fruit storageFruit: Storage.storage) {
            if (storageFruit.getName().equals(line.getFruitName())) {
                if ((storageFruit.getNumber() - line.getQuantity()) < 0) {
                    throw new RuntimeException("You cannot sell that many "
                            + storageFruit.getName());
                } else {
                    int newNumber = storageFruit.getNumber() - line.getQuantity();
                    storageFruit.setNumber(newNumber);
                }
            }
        }
        return true;
    }
}
