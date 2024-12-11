package core.basesyntax.data.servise.ipl;

import core.basesyntax.data.model.FruitTransaction;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int LIST_START = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA = ",";

    @Override
    public List<FruitTransaction> convertToOperation(List<String> reportList) {
        List<FruitTransaction> shopOperationList = new ArrayList<>();
        for (String line : reportList.subList(LIST_START, reportList.size())) {
            String[] parts = line.split(COMMA);
            String codeOperation = parts[OPERATION_INDEX];
            String fruit = parts[FRUIT_INDEX];
            int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
            shopOperationList.add(
                    new FruitTransaction(OperationType. fromCode(codeOperation),
                            fruit, quantity));
        }
        return shopOperationList;
    }
}
