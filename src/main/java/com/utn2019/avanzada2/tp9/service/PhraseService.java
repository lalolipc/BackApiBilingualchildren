package com.utn2019.avanzada2.tp9.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn2019.avanzada2.tp9.repository.PhraseRepository;
import com.utn2019.avanzada2.tp9.domain.Phrase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseService {
    private final PhraseRepository productsRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws Exception {
        log.info("loading products...");
        productsRepository.deleteAll();
        // @formatter:off
        final List<Phrase> products = objectMapper.readValue(this.getClass().getResourceAsStream("/phrase-data.json"), new TypeReference<>() {});
        // @formatter:on
        productsRepository.saveAll(products);
    }

    public List<Phrase> getAll(Integer page, Integer size, String direction, String orderBy) {
        return ofNullable(page)
                .map(p -> getPaginated(p, size, direction, orderBy))
                .orElseGet(this::getAll);
    }




    public Long total() {
        return productsRepository.count();
    }

    private List<Phrase> getPaginated(Integer page, Integer size, String direction, String orderBy) {
        final PageRequest request = PageRequest.of(
                page,
                ofNullable(size).orElse(10),
                fromOptionalString(direction).orElse(ASC),
                ofNullable(orderBy).orElse("phraseId")
        );
        return iterableToList(productsRepository.findAll(request));
    }

    private List<Phrase> getAll() {
        return iterableToList(productsRepository.findAll());
    }

    private static List<Phrase> iterableToList(Iterable<Phrase> products) {
        return stream(products.spliterator(), false).collect(toList());
    }

    public List<Phrase> read() {
        return productsRepository.findAll();
    }
}
