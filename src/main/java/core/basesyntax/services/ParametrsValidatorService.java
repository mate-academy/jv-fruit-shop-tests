package core.basesyntax.services;

import java.util.List;

public interface ParametrsValidatorService {
    boolean isValidParameters(List<String> parametrs);

    boolean isParametrsNotNull(Object paramers);
}
