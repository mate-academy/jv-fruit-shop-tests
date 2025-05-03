package service.impl;

import database.Storage;
import service.ReportGenerator;
import service.exceptions.EmptyStorageException;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String TITLE = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String getReport() {
        if (Storage.getAssortment().isEmpty()) {
            throw new EmptyStorageException("There is no transaction stored in the database.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE).append(System.lineSeparator());
        Storage.getAssortment().forEach((key, value) -> stringBuilder.append(key)
                .append(COMMA).append(value).append(System.lineSeparator()));
        return stringBuilder.toString();
    }
}
