package core.basesyntax.service.impl;

import core.basesyntax.exception.WrongFileType;
import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriterImpl implements Writer {
    private static final String NEW_HEADER = "fruit,quantity";
    private static final String FILE_FORMAT = ".csv";
    private final Map<String, Integer> mapToWrite;

    public WriterImpl(Map<String, Integer> mapToWrite) {
        this.mapToWrite = mapToWrite;
    }

    @Override
    public void write(String toFileName) {
        if (!toFileName.endsWith(FILE_FORMAT)) {
            throw new WrongFileType("Cant write info to not csv file");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write(NEW_HEADER);
            writer.newLine();
            for (Map.Entry<String, Integer> entry : mapToWrite.entrySet()) {
                String line = entry.getKey() + "," + entry.getValue();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cant write data to file" + toFileName);
        }
    }
}
