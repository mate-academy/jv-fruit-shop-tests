package core.basesyntax.service.parser;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FruitRecordDtoParserImpl implements FruitRecordDtoParser {
    private static final String NEGATIVE_VALUE_EXCEPTION = "Quantity can't be less than 0";
    private static final String INSUFFICIENT_NUMBER_OF_VALUES = "Current line has less "
            + "than three elements";
    private static final String INVALID_FRUIT_NAME = "Fruit name should only "
            + "consists of letters";
    private static final String SPLITTER = ",";
    private static final Pattern MATCH_LETTERS = Pattern.compile("[a-zA-z]*");
    private static final int OPERATION_TYPE = 0;
    private static final int SKIP_HEADLINE = 1;
    private static final int FRUIT_NAME = 1;
    private static final int QUANTITY = 2;

    @Override
    public List<FruitRecordDto> parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        return lines.stream()
                .skip(SKIP_HEADLINE)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private FruitRecordDto parseLine(String currentLine) {
        String operationType;
        String fruitName;
        String[] currentLineArray = currentLine.split(SPLITTER);
        if (currentLineArray.length < 3) {
            throw new RuntimeException(INSUFFICIENT_NUMBER_OF_VALUES);
        }
        operationType = Operation.getOperationByLetter(currentLineArray[OPERATION_TYPE].trim())
                .getOperation();
        fruitName = currentLineArray[FRUIT_NAME].trim();
        if (!MATCH_LETTERS.matcher(fruitName).matches()) {
            throw new RuntimeException(INVALID_FRUIT_NAME);
        }
        int quantity = Integer.parseInt(currentLineArray[QUANTITY].trim());
        if (quantity < 0) {
            throw new RuntimeException(NEGATIVE_VALUE_EXCEPTION);
        }
        return new FruitRecordDto(Operation.getOperationByLetter(operationType),
                new Fruit(fruitName), quantity);
    }
}
