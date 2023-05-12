package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.service.ReadService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReadServiceImpl implements ReadService {
    private static final String COMMA = ",";
    private static final int AMOUNT_OF_DATA_PER_LINE = 3;
    
    @Override
    public List<String> readFile(Path path) {
        List<String> fileData = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String value = reader.readLine();
            checkEmptyData(value);
            value = reader.readLine();
            
            while (value != null) {
                checkCorrectDataAndThrowAnException(value);
                fileData.add(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + path.toFile(), e);
        }
        return fileData;
    }
    
    private void checkCorrectDataAndThrowAnException(String value) {
        for (String data : value.split(COMMA)) {
            checkNullValue(data, value.split(COMMA));
            checkEmptyValue(data, value.split(COMMA));
            checkArrayValueLength(value.split(COMMA));
        }
    }
    
    private void checkNullValue(String value, String[] arrayValue) {
        if (Objects.equals(value, "null")) {
            throw new ValidationException("Invalid input data, data must not be null, but was: "
                    + Arrays.toString(arrayValue));
        }
    }
    
    private void checkEmptyValue(String value, String[] arrayValue) {
        if (value.length() == 0) {
            throw new ValidationException("Invalid input data, data must not be empty, but was: "
                    + Arrays.toString(arrayValue));
        }
    }
    
    private void checkArrayValueLength(String[] arrayValue) {
        if (arrayValue.length != AMOUNT_OF_DATA_PER_LINE) {
            throw new ValidationException("Invalid input data, data length must be 3, but was: "
                    + Arrays.toString(arrayValue));
        }
    }
    
    private void checkEmptyData(String data) {
        if (data == null) {
            throw new ValidationException("Invalid input data, data must not be empty");
        }
    }
}
