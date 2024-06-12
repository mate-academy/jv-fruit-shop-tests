package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitRecordParserService;
import core.basesyntax.service.validator.DataValidator;
import java.util.ArrayList;
import java.util.List;

public class FruitRecordParserServiceImpl implements FruitRecordParserService {
    public static final int TITLE_INDEX = 0;
    public static final int VALID_STRING_INDEX = 1;
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;

    private DataValidator dataValidator;

    public FruitRecordParserServiceImpl(DataValidator dataValidator) {
        this.dataValidator = dataValidator;
    }

    @Override
    public List<FruitRecordDto> getRecord(List<String> stringsFromFile) {
        List<FruitRecordDto> outputList = new ArrayList<>();
        List<String> listToParse;
        if (dataValidator.test(stringsFromFile.get(TITLE_INDEX))) {
            listToParse = stringsFromFile;
        } else {
            listToParse = stringsFromFile.subList(VALID_STRING_INDEX, stringsFromFile.size());
        }
        for (String string : listToParse) {
            if (!dataValidator.test(string)) {
                throw new InvalidInputException("Invalid line in input file: " + string);
            }
            String[] stringElements = string.split(DataValidator.CSV_SEPARATOR);
            outputList.add(new FruitRecordDto(stringElements[OPERATION_INDEX],
                    stringElements[FRUIT_INDEX],
                    Integer.parseInt(stringElements[QUANTITY_INDEX])));
        }
        return outputList;
    }
}
