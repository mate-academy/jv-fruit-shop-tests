package core.basesyntax.service;

public class FileFormatValidator {

    public void validate(String firstLine) {
        if (!firstLine.startsWith("type,fruit,quantity")) {
            throw new RuntimeException("Invalid format of input data:" + firstLine);
        }
    }
}
