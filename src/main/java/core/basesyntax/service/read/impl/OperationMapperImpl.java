package core.basesyntax.service.read.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.read.OperationMapper;
import java.util.Arrays;

public class OperationMapperImpl implements OperationMapper {

    @Override
    public FruitTransaction.Operation mapToOperation(String value) {
        return Arrays.stream(FruitTransaction.Operation.values())
                .filter(x -> x.getOperation().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no such operation"));
    }
}
