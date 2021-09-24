package core.basesyntax.dto;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FruitRecordParser implements ParseService {
    private static final String RECORD_SEPARATOR = System.lineSeparator();
    private static final String TITLE = "\\w,(\\w+),(\\w+)";
    private static final String DATA_SEPARATOR = ",";
    private static final int APPEND_TO_REMOVE_TITLE = 2;

    @Override
    public List<FruitRecord> parseFromCsv(String datInString) {
        if (datInString == null || datInString.length() == 0) {
            throw new RuntimeException(
                    "Invalid data in parseFromCsv(String datInString)"
                            + datInString);
        }
        if (datInString.substring(0, datInString
                .indexOf(RECORD_SEPARATOR)).matches(TITLE)) {
            throw new RuntimeException(
                    "There is no title in "
                            + datInString);
        }
        List<FruitRecord> listOfRecords;
        datInString = datInString.substring(datInString.indexOf(RECORD_SEPARATOR)
                + APPEND_TO_REMOVE_TITLE);
        String[] arrOfRecords = datInString.split(RECORD_SEPARATOR);
        listOfRecords = Arrays.stream(arrOfRecords)
                .map(String::trim)
                .map(this::parseIntoFruitRecord)
                .collect(Collectors.toList());
        return listOfRecords;
    }

    private FruitRecord parseIntoFruitRecord(String recordInString) {
        String[] dataInArray = recordInString.split(DATA_SEPARATOR);
        char typeOfOperation = dataInArray[0].charAt(0);
        String nameOfFruit = dataInArray[1];
        int amountOfFruits = Integer.parseInt(dataInArray[2]);
        return new FruitRecord(typeOfOperation, nameOfFruit, amountOfFruits);
    }

    @Override
    public String parseIntoCsv(Set<Fruit> dataToString) {
        if (dataToString == null) {
            throw new RuntimeException(
                    "passing null in parseIntoCsv(Set<Fruit> dataToString)");
        }
        if (dataToString.contains(null)) {
            throw new RuntimeException(
                    "passing null in Set in"
                            + " parseIntoCsv(Set<Fruit> dataToString)");
        }
        StringBuilder stringBuilderOfOutPutData = new StringBuilder();
        for (Fruit fruit : dataToString) {
            stringBuilderOfOutPutData.append(fruit.getName());
            stringBuilderOfOutPutData.append(DATA_SEPARATOR);
            stringBuilderOfOutPutData.append(fruit.getAmount());
            stringBuilderOfOutPutData.append(RECORD_SEPARATOR);
        }
        return stringBuilderOfOutPutData.toString().trim();
    }
}
