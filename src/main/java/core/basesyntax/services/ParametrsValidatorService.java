package core.basesyntax.services;

import core.basesyntax.exceptions.InvalidParametersException;
import java.util.List;

public interface ParametrsValidatorService {
    boolean isValidParameters(List<String> parametrs);

    boolean isParametrsNotNull(Object paramers);

    boolean isOperationValid(String operation);

    static boolean isParametrPositive(int saldo, int quantity) {
        if (saldo < 0 || quantity < 0) {
            throw new InvalidParametersException("Parametrs can't be negative");
        }
        return true;
    }
}
