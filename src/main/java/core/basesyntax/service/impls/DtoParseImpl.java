package core.basesyntax.service.impls;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DtoParser;
import exceptions.IncorrectInputLineException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DtoParseImpl implements DtoParser {
    private static final int OPERATION = 0;
    private static final int FRUIT_NAME = 1;
    private static final int QUANTITY = 2;
    private static final int CSV_COLUMNS_NUMBER = 3;
    private static final String CSV_SEPARATOR = ",";
    private static final Set<String> LEGAL_OPERATIONS
            = new HashSet<>(Arrays.asList("b", "p", "s", "r"));

    @Override
    public List<FruitRecordDto> parse(List<String> fileLines) {
        if (fileLines.isEmpty()) {
            return new ArrayList<>();
        }
        List<FruitRecordDto> fruitDto = new ArrayList<>(fileLines.size());
        String[] tmpLineRecord;

        for (String fileLine : fileLines) {
            tmpLineRecord = fileLine.split(CSV_SEPARATOR);
            if (tmpLineRecord.length != CSV_COLUMNS_NUMBER
                    || tmpLineRecord[OPERATION].length() != 1
                    || !LEGAL_OPERATIONS.contains(tmpLineRecord[OPERATION])) {
                throw new IncorrectInputLineException("Incorrect input data.");
            }

            fruitDto.add(new FruitRecordDto(tmpLineRecord[OPERATION].toLowerCase().trim(),
                    tmpLineRecord[FRUIT_NAME].toLowerCase().trim(),
                    Integer.valueOf(tmpLineRecord[QUANTITY])));
        }
        return fruitDto;
    }
}
