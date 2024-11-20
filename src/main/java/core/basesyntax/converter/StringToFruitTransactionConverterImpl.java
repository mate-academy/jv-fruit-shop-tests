package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class StringToFruitTransactionConverterImpl implements Converter<String, FruitTransaction> {

    private final StringToFruitValidator validator = new StringToFruitValidator();

    @Override
    public FruitTransaction convert(String s) {
        String[] arr = s.split(",");
        if (!validator.validateInputData(arr)) {
            throw new IllegalArgumentException("Error while parsing string [" + s + "]");
        }
        final int indexOfOperation = 0;
        final int indexOfName = 1;
        final int indexOfQuantity = 2;
        return new FruitTransaction(
                FruitTransaction.Operation.fromCode(arr[indexOfOperation]),
                arr[indexOfName], Integer.parseInt(arr[indexOfQuantity]));
    }

    @Override
    public List<FruitTransaction> convertList(List<String> list) {
        return list.stream()
                .skip(checkIsLineATitle(list.get(0)) ? 1 : 0)
                .map(this::convert)
                .toList();
    }

    boolean checkIsLineATitle(String s) {
        return s.contains("type")
                || s.contains("fruit")
                || s.contains("quantity");
    }
}
