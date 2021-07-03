package core.basesyntax.service.operation;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.exception.ValueFormatException;
import core.basesyntax.model.Product;
import java.util.Optional;

public class OperationIncreaseHandler implements OperationHandler {
    private final ProductDao productDao;

    public OperationIncreaseHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public int apply(int amount, Product key) {
        if (amount < 0) {
            throw new ValueFormatException("The value cannot be less than zero. Value, " + amount);
        }
        Optional<Integer> fruitQuantity = productDao.get(key);
        return fruitQuantity.isPresent() ? fruitQuantity.get() + amount : amount;
    }
}
