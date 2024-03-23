package core.basesyntax.service;

import core.basesyntax.exeptions.IncorrectDataFileExeption;
import java.util.List;

public interface DataParser<T> {
    List<T> parse(List<String> data) throws IncorrectDataFileExeption;
}
