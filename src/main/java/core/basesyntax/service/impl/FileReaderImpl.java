package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> readData(String filePath) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from " + filePath, e);
        }
        return Stream.of(builder).map(StringBuilder::toString).collect(Collectors.toList());
    }
}
