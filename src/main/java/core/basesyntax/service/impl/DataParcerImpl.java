package core.basesyntax.service.impl;

import core.basesyntax.fruit.FruitTransaction;
import core.basesyntax.service.DataParcer;
import java.util.ArrayList;
import java.util.List;

public class DataParcerImpl implements DataParcer {
    private static final int HEAD_OF_FILE = 0;

    @Override
    public List<FruitTransaction> getFruitsMoving(List<String> list) {
        list.remove(HEAD_OF_FILE);
        List<FruitTransaction> listFruitsMoving = new ArrayList<>();
        String[] fields;
        for (String line : list) {
            fields = line.split(",");
            if (fields[0].matches("[^bpsr]")) {
                throw new RuntimeException("Unknown type of action:" + fields[0]);
            }
            listFruitsMoving
                    .add(new FruitTransaction(fields[0], fields[1], Integer.parseInt(fields[2])));
        }
        return listFruitsMoving;
    }
}
