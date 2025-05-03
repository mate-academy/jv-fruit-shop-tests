package core.basesyntax.db;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<String> listOfData = new ArrayList<>();

    public List<String> getListOfData() {
        return listOfData;
    }
}
