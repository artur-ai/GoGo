package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Country;
import com.maiboroda.GoGo.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country ukraine;
    private Country poland;
    private Country germany;

    @BeforeEach
    void setUp() {
        ukraine = new Country();
        ukraine.setId(1L);
        ukraine.setName("Ukraine");

        poland = new Country();
        poland.setId(2L);
        poland.setName("Poland");

        germany = new Country();
        germany.setId(3L);
        germany.setName("Germany");
    }

    @Test
    void testShouldLoadAllCountriesIntoCache() {
        List<Country> countries = Arrays.asList(ukraine, poland, germany);
        when(countryRepository.findAll()).thenReturn(countries);

        countryService.initCache();

        verify(countryRepository, times(1)).findAll();

        Country foundUkraine = countryService.getCountryByName("Ukraine");
        Country foundPoland = countryService.getCountryByName("Poland");
        Country foundGermany = countryService.getCountryByName("Germany");

        assertNotNull(foundUkraine);
        assertNotNull(foundPoland);
        assertNotNull(foundGermany);
        assertEquals("Ukraine", foundUkraine.getName());
        assertEquals("Poland", foundPoland.getName());
        assertEquals("Germany", foundGermany.getName());
    }

    @Test
    void testGetCountryByName_ShouldReturnCountry() {
        List<Country> countries = Arrays.asList(ukraine, poland);
        when(countryRepository.findAll()).thenReturn(countries);
        countryService.initCache();

        Country result = countryService.getCountryByName("Ukraine");

        assertNotNull(result);
        assertEquals("Ukraine", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetCountryByName_ShouldThrowException() {
        List<Country> countries = Arrays.asList(ukraine, poland);
        when(countryRepository.findAll()).thenReturn(countries);
        countryService.initCache();

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> countryService.getCountryByName("NonExistent")
        );

        assertEquals("Can not find country: NonExistent", exception.getMessage());
    }

    @Test
    void testGetCountryByName_EmptyCache() {
        when(countryRepository.findAll()).thenReturn(List.of());
        countryService.initCache();

        assertThrows(EntityNotFoundException.class,
                () -> countryService.getCountryByName("Ukraine"));
    }

    @Test
    void testInitCache_ShouldClearAndReload() {
        List<Country> firstLoad = Arrays.asList(ukraine);
        List<Country> secondLoad = Arrays.asList(poland, germany);

        when(countryRepository.findAll())
                .thenReturn(firstLoad)
                .thenReturn(secondLoad);

        countryService.initCache();
        Country firstResult = countryService.getCountryByName("Ukraine");

        countryService.initCache();

        verify(countryRepository, times(2)).findAll();

        assertThrows(EntityNotFoundException.class,
                () -> countryService.getCountryByName("Ukraine"));

        assertNotNull(countryService.getCountryByName("Poland"));
        assertNotNull(countryService.getCountryByName("Germany"));
    }

    @Test
    void testGetCountryByName_ShouldThrowExceptionForWrongCase() {
        List<Country> countries = Arrays.asList(ukraine);
        when(countryRepository.findAll()).thenReturn(countries);
        countryService.initCache();

        assertThrows(EntityNotFoundException.class,
                () -> countryService.getCountryByName("ukraine"));

        assertThrows(EntityNotFoundException.class,
                () -> countryService.getCountryByName("UKRAINE"));
    }
}