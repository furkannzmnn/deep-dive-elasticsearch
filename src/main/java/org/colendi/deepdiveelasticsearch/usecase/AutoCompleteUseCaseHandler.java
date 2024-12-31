package org.colendi.deepdiveelasticsearch.usecase;

import lombok.RequiredArgsConstructor;
import org.colendi.deepdiveelasticsearch.port.AutoCompletePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoCompleteUseCaseHandler {

    private final AutoCompletePort autoCompletePort;

    public List<String> autoComplete(String query) {
        return autoCompletePort.autoComplete(query);
    }
}
