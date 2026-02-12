package core.basesyntax.service.impl;

import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String SEPARATOR = ",";
    private static final String TITLE_SAMPLE = "type,fruit,quantity";

    @Override
    public List<FruitTransfer> convertToTransfer(List<String> inputReport) {
        if (inputReport == null) {
            throw new InvalidDataException("Null value is not allowed");
        }
        if (inputReport.isEmpty()) {
            throw new InvalidDataException("Empty inputReport is not allowed");
        }
        if (!TITLE_SAMPLE.equals(inputReport.get(0))) {
            throw new InvalidDataException("Incorrect title format in CSV file!");
        }
        List<FruitTransfer> transferList = new ArrayList<>();
        List<String[]> collectedLinesInfo = inputReport.stream()
                .map(s -> s.split(SEPARATOR))
                .skip(1)
                .toList();

        for (String[] line : collectedLinesInfo) {
            boolean isDigits = line[AMOUNT_INDEX].codePoints().allMatch(Character::isDigit);
            if (!isDigits) {
                throw new InvalidDataException("Incorrect quantity format in CSV file."
                        + " Shall be only a numeric value!");
            }
            Operation operations = Operation.getOperation(line[OPERATION_INDEX]);
            String fruit = line[FRUIT_INDEX];
            int quantity = Integer.parseInt(line[AMOUNT_INDEX]);
            transferList.add(new FruitTransfer(operations, fruit, quantity));
        }
        return transferList;
    }
}
