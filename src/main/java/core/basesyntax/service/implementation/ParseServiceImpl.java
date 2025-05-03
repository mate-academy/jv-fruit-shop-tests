package core.basesyntax.service.implementation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.List;
import java.util.stream.Collectors;

public class ParseServiceImpl implements ParseService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null) {
            throw new RuntimeException("List cannot be null");
        }
        return lines.stream()
                .skip(1)
                .map(s -> s.split(","))
                .map(splittedLines -> {
                    FruitTransaction fruitTransaction = new FruitTransaction();
                    fruitTransaction.setOperation(FruitTransaction.Operation
                            .findByLetter(splittedLines[OPERATION_INDEX]));
                    fruitTransaction.setFruit(splittedLines[FRUIT_TYPE_INDEX]);
                    fruitTransaction.setQuantity(Integer.parseInt(splittedLines[QUANTITY_INDEX]));
                    return fruitTransaction;
                })
                .collect(Collectors.toList());
    }
}
