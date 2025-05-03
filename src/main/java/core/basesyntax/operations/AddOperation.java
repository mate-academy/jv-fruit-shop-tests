package core.basesyntax.operations;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;

public class AddOperation implements Operation {
    @Override
    public void apply(ProductDto productDto) {
        if (productDto.getFruitName() == null || productDto.getQuantity() == 0) {
            throw new RuntimeException("Invalid input");
        }

        Integer newQuantity =
                productDto.getQuantity() + Storage.fruits.get(productDto.getFruitName());
        Storage.fruits.put(productDto.getFruitName(), newQuantity);
    }
}
