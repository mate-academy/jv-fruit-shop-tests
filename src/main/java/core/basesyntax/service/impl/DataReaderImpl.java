package core.basesyntax.service.impl;

import core.basesyntax.service.DataReader;
import core.basesyntax.service.impl.exception.InvalidDataException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReaderImpl implements DataReader {
    @Override
    public String readDataFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data = bufferedReader.readLine();
            while (data != null) {
                builder.append(data)
                        .append(System.lineSeparator());
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Can't find file by path " + filePath);
        }
        return builder.toString().trim();
    }
}
