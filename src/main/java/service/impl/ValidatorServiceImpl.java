package service.impl;

import java.util.List;
import service.ValidatorService;

public class ValidatorServiceImpl implements ValidatorService {
    private static final int NUMBER_OF_COLUMN = 3;
    private static final int INDEX_OF_QUANTITY = 2;

    @Override
    public boolean isValidData(List<String> inputData) {
        if (inputData.size() == 0
                || inputData.get(0).equals("type,fruit,quantity") && inputData.size() == 1) {
            throw new RuntimeException("no any action");
        }
        if (inputData.get(0).equals("type,fruit,quantity")) {
            inputData.remove(0);
        }
        for (String string : inputData) {
            String[] strings = string.split(",");
            int quantity;
            quantity = Integer.parseInt(strings[INDEX_OF_QUANTITY]);
            if (strings.length != NUMBER_OF_COLUMN || quantity < 0) {
                throw new RuntimeException("Invalid data");
            }
        }
        return true;
    }
}
