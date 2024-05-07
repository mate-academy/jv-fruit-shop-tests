package core.basesyntax.servise.impl;

import core.basesyntax.exception.ParserServiceException;
import core.basesyntax.servise.ParserService;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParserServiceImpl implements ParserService {
    private static final int OFFSET = 1;
    private static final String SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private final Predicate<String[]> predicateMissingData;
    private final Function<String, Integer> functionCheckQuantity;

    public ParserServiceImpl() {
        predicateMissingData = array -> array.length < 3
                || array[FRUIT_INDEX].equals("")
                || array[TYPE_INDEX].equals("");
        functionCheckQuantity = quantity -> {
            try {
                return Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                throw new ParserServiceException("Number Format is wrong:" + quantity, e);
            }
        };
    }

    @Override
    public List<FruitTransaction> parsingData(List<String> inputList) {
        if (inputList == null || inputList.isEmpty()) {
            throw new ParserServiceException("List is Null or empty");
        }
        return inputList.stream()
                .skip(OFFSET)
                .map(line -> {
                    String[] array = line.split(SEPARATOR);
                    if (predicateMissingData.test(array)) {
                        throw new ParserServiceException("Missing data or "
                                + "the length of the data line is:" + array.length);
                    }
                    return new FruitTransaction(
                            array[TYPE_INDEX],
                            array[FRUIT_INDEX],
                            functionCheckQuantity.apply(array[QUANTITY_INDEX]));
                })
                .collect(Collectors.toList());
    }
}
