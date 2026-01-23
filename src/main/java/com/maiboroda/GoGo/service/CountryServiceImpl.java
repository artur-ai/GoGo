package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Country;
import com.maiboroda.GoGo.repository.CountryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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


        countries.forEach(country ->
                countryCache.put(country.getName().toLowerCase(), country)
        );

        log.info("Downloaded {} countries in cache ", countries.size());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void refreshCache() {
        countryCache.clear();
        log.info("Starting scheduled cache refresh for countries...");

        try {
            List<Country> countries = countryRepository.findAll();
            countries.forEach(country -> {
                countryCache.put(country.getName().toLowerCase(), country);
            });
            log.info("Cache updated successfully. Countries in cache {}", countryCache.size());
        } catch (Exception exception) {
            log.error("Failed to refresh country cache", exception);
        }
    }

    @Override
    public Country getCountryByName(String name) {
        if (name == null) {
            throw new EntityNotFoundException("Country name cannot be null");
        }
        log.info("Searching country {} in cache", name);

        Country country = countryCache.get(name.toLowerCase());
        if (country == null) {
            throw new EntityNotFoundException("Can not find country: " + name);
        }

        return country;
    }
}
