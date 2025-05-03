package core.basesyntax.service.operation;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.exception.EmptyStorageException;
import core.basesyntax.exception.ValueFormatException;
import core.basesyntax.model.Product;
import java.util.Optional;

public class OperationDecreaseHandler implements OperationHandler {
    private static final String VALUE_FORMAT_EXCEPTION_MESSAGE =
            "You can't buy more fruits than in storage";
    private static final String EMPTY_STORAGE_EXCEPTION_MESSAGE =
            "You cant purchase product because storage is empty";
    private final ProductDao productDao;

    public OperationDecreaseHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public int apply(int amount, Product key) {
        if (productDao.getAll().isEmpty()) {
            throw new EmptyStorageException(EMPTY_STORAGE_EXCEPTION_MESSAGE);
        }
        Optional<Integer> fruitQuantity = productDao.get(key);
        if (fruitQuantity.isPresent() && fruitQuantity.get() >= amount) {
            return fruitQuantity.get() - amount;
        }
        throw new ValueFormatException(VALUE_FORMAT_EXCEPTION_MESSAGE + "[" + amount + "]");
    }
}
