package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Country;
import com.maiboroda.GoGo.repository.CountryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Map<String, Country> countryCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initCache() {
        log.info("Loading countries in cache...");

        List<Country> countries = countryRepository.findAll();
        countryCache.clear();


        countries.forEach(country ->
                countryCache.put(country.getName(), country)
                );

        log.info("Downloaded {} countries in cache ", countries.size());
    }

    @Override
    public Country getCountryByName(String name) {
        log.info("Searching country {} in cache", name);

        Country country = countryCache.get(name);
        if (country == null) {
            throw new EntityNotFoundException("Can not find country: " + name);
        }

        return country;
    }
}
