package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String REGEX = ",";
    private static final int INDEX_CODE_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY_OPERATION = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputFile) {
        if (inputFile == null || inputFile.isEmpty() || inputFile.size() <= 1) {
            return List.of();
        }

        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = 1; i < inputFile.size(); i++) {
            String[] parts = inputFile.get(i).split(",");
            if (parts.length != 3) {
                throw new RuntimeException("Incorrect format of FruitTransaction! Line of report: "
                        + i + "; It should contains operation, fruit, quantity !");
            }
            try {
                Operation operation = Operation.operationFromCode(parts[INDEX_CODE_OPERATION]);
                String fruit = parts[INDEX_FRUIT];
                int quantity = Integer.parseInt(parts[INDEX_QUANTITY_OPERATION]);
                transactions.add(new FruitTransaction(operation, fruit, quantity));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid format data of FruitTransaction in "
                        + "quantity! Line of the report: " + i);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid format data of FruitTransaction in "
                        + "fruit name! Line of the report:" + i);
            }
        }
        return transactions;
    }
}

//
//
//        return inputFile.stream()
//                .skip(1)
//                .map(fileLine -> fileLine.split(REGEX))
//                .map(splits -> {
//                    List<FruitTransaction> splits1 = new ArrayList<>();
//                    try {
//                        new FruitTransaction(Operation
//                                .operationFromCode(splits1[INDEX_CODE_OPERATION]),
//                                splits[INDEX_FRUIT],
//                                Integer.parseInt(splits[INDEX_QUANTITY_OPERATION]));)
//                    } catch (IllegalArgumentException e) {
//                        throw new RuntimeException(e);
//                    }
//                    }
//
//                .toList();
//}
//}
