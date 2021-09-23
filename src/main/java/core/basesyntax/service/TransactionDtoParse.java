package core.basesyntax.service;

import core.basesyntax.service.impl.TransactionDto;
import java.util.List;

public interface TransactionDtoParse {
    List<TransactionDto> parseData(List<String> data);
}
