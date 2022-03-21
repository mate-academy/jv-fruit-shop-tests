package core.basesyntax.service.operation;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.validation.Validator;
import core.basesyntax.service.validation.ValidatorImpl;
import java.util.ArrayList;
import java.util.List;

public class FruitParser {
    private static final int TYPE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String SPLIT_SYMBOL = ",";
    private Validator lineValidator = new ValidatorImpl();

    public List<FruitRecord> createDto(List<String> list) {
        List<FruitRecord> fruitRecords = new ArrayList<>();
        for (String newSplit : list) {
            String[] split = newSplit.split(SPLIT_SYMBOL);
            lineValidator.validate(split);
            fruitRecords.add(new FruitRecord(split[TYPE_INDEX],
                    new Fruit(split[NAME_INDEX]),
                    Integer.parseInt(split[AMOUNT_INDEX])));
        }
        return fruitRecords;
    }
}
