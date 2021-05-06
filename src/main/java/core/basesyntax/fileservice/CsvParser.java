package core.basesyntax.fileservice;

import core.basesyntax.dto.ProductDto;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements Parser {
    private static final int OPERATION = 0;
    private static final int FRUIT_NAME = 1;
    private static final int FRUIT_QUANTITY = 2;
    private static final int DATA_LENGTH = 3;
    private static final String CSV_SEPARATOR = ",";

    @Override
    public List<ProductDto> parse(List<String> data) {
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("Invalid input");
        }

        ProductDto productDto;
        List<ProductDto> productDtoList = new ArrayList<>();

        for (String datum : data) {
            String[] product = datum.split(CSV_SEPARATOR);

            if (product.length > DATA_LENGTH) {
                continue;
            }

            productDto = new ProductDto(product[OPERATION], product[FRUIT_NAME],
                    Integer.parseInt(product[FRUIT_QUANTITY]));
            productDtoList.add(productDto);
        }

        return productDtoList;
    }
}
