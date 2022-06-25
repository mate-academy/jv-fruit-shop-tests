package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataHandlerImpl implements DataHandler {
    @Override
    public List<FruitTransaction> handleData(List<String> data) {
        if (data.isEmpty() || data.size() == 1) {
            throw new RuntimeException("Input data for handling is invalid");
        }
        return dataParser(data).stream()
                .map(i -> new FruitTransaction(
                        (i[0]), i[1], Integer.parseInt(i[2])))
                .collect(Collectors.toList());
    }

    private List<String[]> dataParser(List<String> storageData) {
        storageData.remove(0);
        List<String[]> update = new ArrayList<>();
        for (String string : storageData.toArray(new String[0])) {
            update.add(string.split(","));
        }
        return update;
    }

}
