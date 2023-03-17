package core.basesyntax.services.impl;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.ParametrsValidatorService;
import java.util.List;

public class ParametrsValidatorServiseImpl implements ParametrsValidatorService {
    public static final int PARAMETRS_COUNT = 3;
    public static final int OPERATION_INDEX = 0;
    public static final int QUANTITY_INDEX = 2;
    public static final int FRUIT_INDEX = 1;
    private final List<String> opratorTypeCode;
    private final List<String> fruitsInStore;

    public ParametrsValidatorServiseImpl(List<String> opratorTypeCode, List<String> fruitsInStore) {
        this.opratorTypeCode = opratorTypeCode;
        this.fruitsInStore = fruitsInStore;
    }

    @Override
    public boolean isValidParameters(List<String> parametrs) {
        isParametrsNotNull(parametrs);
        if (parametrs.size() != PARAMETRS_COUNT) {
            throw new InvalidParametersException("Invalid parameters: " + parametrs);
        }
        isOperationValid(parametrs.get(OPERATION_INDEX));
        isFruitValid(parametrs.get(FRUIT_INDEX));
        isQuantityValid(parametrs.get(QUANTITY_INDEX));
        return parametrs.size() == PARAMETRS_COUNT;
    }

    @Override
    public boolean isParametrsNotNull(Object paramers) {
        if (paramers == null) {
            throw new NullDataException("Can't parse data when input data is null");
        }
        return true;
    }

    private boolean isOperationValid(String operation) {
        if (!opratorTypeCode.contains(operation)) {
            throw new InvalidParametersException("Operation can't be: " + operation);
        }
        return true;
    }

    private boolean isFruitValid(String fruit) {
        if (!fruitsInStore.contains(fruit)) {
            throw new InvalidParametersException("We don't have such fruit: "
                    + fruit + " in our store.");
        }
        return true;
    }

    private boolean isQuantityValid(String quantity) {
        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Quantity can't be: " + quantity);
        }
        if (Integer.parseInt(quantity) < 0) {
            throw new InvalidParametersException("Quantity can't be negative: " + quantity);
        }
        return true;
    }
}
