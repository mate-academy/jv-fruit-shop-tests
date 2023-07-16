package core.basesyntax.service;

import core.basesyntax.model.*;

import java.util.*;

public interface DataParserService {
    List<FruitTransaction> parseData(List<String> retrievedLines);
}
