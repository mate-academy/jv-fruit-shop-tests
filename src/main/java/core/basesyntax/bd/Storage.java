package core.basesyntax.bd;

import core.basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static final List<FruitRecordDto> records = new ArrayList<>();
    public static final Map<String, Integer> fruitQuantity = new HashMap<>();
}
