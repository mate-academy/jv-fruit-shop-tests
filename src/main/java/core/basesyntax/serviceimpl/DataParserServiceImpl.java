package core.basesyntax.serviceimpl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParserService;
import java.util.List;

public class DataParserServiceImpl implements DataParserService {
    private static final String SPLITERATOR = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> getTransactions(List<String> textReport) {
        textReportValidation(textReport);
        return textReport.stream()
                .map(str -> str.split(SPLITERATOR))
                .map(strArr -> new FruitTransaction(Operation.getOperationType(
                        strArr[OPERATION_TYPE_INDEX]), strArr[FRUIT_TYPE_INDEX],
                        Integer.parseInt(strArr[FRUIT_QUANTITY_INDEX])))
                .toList();
    }

    private void textReportValidation(List<String> list) {
        for (String s : list) {
            String[] splittedFields = s.split(SPLITERATOR);
            if (splittedFields.length != 3) {
                throw new InvalidDataException("Invalid input data, amount of fields"
                        + " is more or less then 3:" + splittedFields.length);
            }
            try {
                Integer.parseInt(splittedFields[FRUIT_QUANTITY_INDEX]);
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Invalid input data, fruit quantity"
                        + " have to be a number, but was: " + splittedFields[FRUIT_QUANTITY_INDEX]);
            }
            if (Integer.parseInt(splittedFields[FRUIT_QUANTITY_INDEX]) < 0) {
                throw new InvalidDataException("Invalid input data, fruit quantity "
                        + "can't be less then 0: " + splittedFields[FRUIT_QUANTITY_INDEX]);
            }
            if (!Operation.validateOperationType(splittedFields[OPERATION_TYPE_INDEX])) {
                throw new InvalidDataException("Invalid input data, operation type is not found: "
                        + splittedFields[OPERATION_TYPE_INDEX]);
            }
        }
    }
}
