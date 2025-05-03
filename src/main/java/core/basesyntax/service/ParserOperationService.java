package core.basesyntax.service;

import core.basesyntax.fruitscontent.FruitTransaction;
import java.util.List;

public interface ParserOperationService {
    List<FruitTransaction> parseContentForOperations(List<String> inputData);
}
