package core.basesyntax.services.validation;

import core.basesyntax.model.TransactionDto;

public interface ValidateData {
    TransactionDto isDataOk(String record);
}
