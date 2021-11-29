package service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParsedLine;
import service.OperationService;

public class CreateOperation implements OperationService {
    @Override
    public boolean operation(ParsedLine line) {
        Fruit newFruit = new Fruit(line.getFruitName(), line.getQuantity());
        Storage.storage.add(newFruit);
        return true;
    }
}
