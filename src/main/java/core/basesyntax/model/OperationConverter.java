package core.basesyntax.model;

import com.opencsv.bean.AbstractBeanField;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class OperationConverter extends AbstractBeanField<FruitTransaction.Operation, String> {
    @Override
    protected FruitTransaction.Operation convert(String value) {
        return Arrays.stream(FruitTransaction.Operation.values())
                .filter(operation -> operation.getCode().equals(value))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find operation for code: " + value));
    }
}
