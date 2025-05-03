package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FruitMapperImpl implements FruitMapper {
    private static final String COMMA = ",";
    private static final int INDEX_OF_TITLE = 0;
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_PRODUCT_NAME = 1;
    private static final int INDEX_OF_PRODUCT_AMOUNT = 2;

    @Override
    public List<FruitTransaction> map(List<String> data) {
        List<String> copyOfData = new ArrayList<>(data);
        copyOfData.remove(INDEX_OF_TITLE);
        return copyOfData.stream()
                .map(line -> line.split(COMMA))
                .map(split -> new FruitTransaction(
                        split[INDEX_OF_PRODUCT_NAME],
                        Operation.fromCode(split[INDEX_OF_OPERATION]),
                        Integer.parseInt(split[INDEX_OF_PRODUCT_AMOUNT])
                ))
                .collect(Collectors.toList());

    }
}
