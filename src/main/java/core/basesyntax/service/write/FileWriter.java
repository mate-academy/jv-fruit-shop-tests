package core.basesyntax.service.write;

public interface FileWriter {
    void write(String resultString, String pathToWrite);

    String prepareToWrite();
}
