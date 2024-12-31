package org.colendi.deepdiveelasticsearch.port;

import java.util.List;

public interface AutoCompletePort {
    List<String> autoComplete(String query);
}
