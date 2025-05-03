package core.basesyntax.services.stockservice;

import core.basesyntax.model.TransactionDto;
import java.util.List;

public interface StockService {
    void applyOperationsOnFruitsDto(List<TransactionDto> storageTransactions);
}
