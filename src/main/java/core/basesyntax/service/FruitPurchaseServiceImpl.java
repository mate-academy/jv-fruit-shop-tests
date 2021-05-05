package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.exception.InsufficientAmountException;
import core.basesyntax.service.interfaces.FruitOperationService;
import java.util.Optional;

public class FruitPurchaseServiceImpl implements FruitOperationService {
    private FruitDao fruitDao;

    public FruitPurchaseServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(TransactionDto dto) {
        Optional<Integer> currentQuantity = fruitDao.get(dto.getFruit());
        if (currentQuantity.isPresent() && currentQuantity.get() >= dto.getQuantity()) {
            fruitDao.put(dto.getFruit(), currentQuantity.get()
                    - dto.getQuantity());
            return;
        }
        throw new InsufficientAmountException("Insufficient quantity of goods");
    }
}
