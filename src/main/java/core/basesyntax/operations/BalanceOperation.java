package core.basesyntax.operations;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;

public class BalanceOperation implements Operation {
    @Override
    public void apply(ProductDto fruitDto) {
        if (fruitDto.getFruitName() == null) {
            throw new RuntimeException("Invalid input");
        }

        Storage.fruits.put(fruitDto.getFruitName(), fruitDto.getQuantity());
    }
}
