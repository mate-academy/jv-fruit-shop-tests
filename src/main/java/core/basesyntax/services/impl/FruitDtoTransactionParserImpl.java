package core.basesyntax.services.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.dtos.FruitDtoTransaction;
import core.basesyntax.services.interfaces.DataValidator;
import core.basesyntax.services.interfaces.FruitDtoTransactionParser;
import java.util.ArrayList;
import java.util.List;

public class FruitDtoTransactionParserImpl implements FruitDtoTransactionParser {
    private static final String SPLIT_REGEX = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private DataValidator dataValidator;

    public FruitDtoTransactionParserImpl() {
        dataValidator = new DataValidatorImpl();
    }

    @Override
    public List<FruitDtoTransaction> parse(List<String> lines) {
        List<FruitDtoTransaction> fruitDtoTransactions = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] dtoRecords = line.split(SPLIT_REGEX);
            if (dtoRecords[OPERATION_INDEX].length() != 1) {
                continue;
            }
            dataValidator.checkDtoLine(line);
            fruitDtoTransactions.add(new FruitDtoTransaction(
                    OperationType.getOperationType(dtoRecords[OPERATION_INDEX]),
                    dtoRecords[FRUIT_NAME_INDEX],
                    Integer.parseInt(dtoRecords[QUANTITY_INDEX])));
        }
        return fruitDtoTransactions;
    }

}
