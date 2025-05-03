package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitDto;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionServiceImpl {
    private static final String COMMA_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int FRUIT_QUANTITY = 2;

    public List<FruitDto> parse(String inputData) {
        if (inputData == null || inputData.isEmpty()) {
            throw new IllegalArgumentException("sorry something gone wrong wiht your inputData");
        }
        String[] lines = inputData.split(System.lineSeparator());
        List<FruitDto> fruitList = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            String[] temp = lines[i].split(COMMA_SEPARATOR);
            FruitDto fruitDto = new FruitDto();
            fruitDto.setType(temp[OPERATION_INDEX]);
            fruitDto.setName(temp[FRUIT_INDEX]);
            fruitDto.setQuantity(Integer.parseInt(temp[FRUIT_QUANTITY]));
            fruitList.add(fruitDto);
        }
        return fruitList;
    }
}

