package core.basesyntax.service;

import core.basesyntax.model.ParsedLine;
import java.util.LinkedList;
import java.util.List;
import service.InputDataValidator;
import service.LineParser;

public class LineParserImpl implements LineParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<ParsedLine> lineParcer(List<String> input) {
        InputDataValidator inputValidator = new InputDataValidatorImpl();
        List<ParsedLine> parsedLineList = new LinkedList<>();
        inputValidator.validate(input);
        for (String string: input) {
            String[] values = string.split(",");
            parsedLineList.add(new ParsedLine(values[OPERATION_INDEX],
                    values[FRUIT_INDEX], Integer.parseInt(values[QUANTITY_INDEX])));
        }
        return parsedLineList;
    }
}
