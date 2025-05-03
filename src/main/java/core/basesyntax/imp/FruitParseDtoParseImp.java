package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitParse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FruitParseDtoParseImp implements FruitParse {
    private static final String SPLIT_SYMBOL = ",";
    private static final int PLACE_OPERATION = 0;
    private static final int PLACE_NAME_FRUIT = 1;
    private static final int PLACE_AMOUNT = 2;
    private static final Pattern LETTERS_ALPHABETIC = Pattern.compile("[a-zA-z]*");

    @Override
    public List<FruitRecordDto> parse(List<String> line) {
        if (line.size() == 0) {
            throw new RuntimeException("File empty");
        }
        line.remove(0);
        List<FruitRecordDto> listFruit = new ArrayList<>(line.size());
        FruitRecordDto fruitRecordDto;
        String[] arraySting;
        for (String currentLine : line) {
            arraySting = currentLine.split(SPLIT_SYMBOL);
            if (!LETTERS_ALPHABETIC.matcher(arraySting[PLACE_NAME_FRUIT].trim()).matches()) {
                throw new RuntimeException("Name can't be with number");
            }
            fruitRecordDto = new FruitRecordDto(arraySting[PLACE_OPERATION].trim(),
                    new Fruit(arraySting[PLACE_NAME_FRUIT].trim()),
                    Integer.parseInt(arraySting[PLACE_AMOUNT].trim()));
            listFruit.add(fruitRecordDto);
        }
        return listFruit;
    }
}
