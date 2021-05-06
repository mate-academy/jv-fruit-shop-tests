package core.basesyntax.service.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DataValidation;
import core.basesyntax.service.FruitRecordDtoParser;
import java.util.ArrayList;
import java.util.List;

public class FruitRecordDtoParserImpl implements FruitRecordDtoParser {
    private static final String SPLIT_SIGN = ",";
    private static final int OPERATION_TYPE = 0;
    private static final int FRUIT_TYPE = 1;
    private static final int QUANTITY = 2;
    private final DataValidation dataValidation = new DataValidationImpl();

    @Override
    public List<FruitRecordDto> parse(List<String> lines) {
        dataValidation.checkListNotEmpty(lines);
        List<FruitRecordDto> recordDtos = new ArrayList<>(lines.size());
        for (int i = 1; i < lines.size(); i++) {
            dataValidation.checkLine(lines.get(i));
            String[] splittedValues = lines.get(i).split(SPLIT_SIGN);

            String operationType = splittedValues[OPERATION_TYPE];
            OperationType operation = OperationType.getOperationByShortName(operationType);

            FruitRecordDto dto =
                    new FruitRecordDto(operation,
                            splittedValues[FRUIT_TYPE],
                            Integer.parseInt(splittedValues[QUANTITY]));
            recordDtos.add(dto);
        }
        return recordDtos;
    }
}
