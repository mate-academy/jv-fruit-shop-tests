package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.servise.ParseService;
import java.util.List;
import java.util.stream.Collectors;

public class ParseServiceImpl implements ParseService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public List<FruitTransfer> parseFruitTransfers(List<String> list) {
        if (list == null) {
            throw new RuntimeException("List can't be null");
        }
        return list.stream()
                .skip(1)
                .map(this::getFruitTransfer)
                .collect(Collectors.toList());
    }

    private FruitTransfer getFruitTransfer(String line) {
        String [] arrays = line.split(",");
        return new FruitTransfer(getThisOperation(arrays[OPERATION_INDEX]),
                new Fruit(arrays[FRUIT_INDEX]),Integer.parseInt(arrays[AMOUNT_INDEX]));
    }

    private FruitTransfer.Operation getThisOperation(String name) {
        for (FruitTransfer.Operation operation : FruitTransfer.Operation.values()) {
            if (operation.getOperation().equals(name)) {
                return operation;
            }
        }
        throw new RuntimeException("No such element found");
    }
}
