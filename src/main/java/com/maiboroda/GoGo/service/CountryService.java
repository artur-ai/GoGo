package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Country;

public interface CountryService {
    Country getCountryByName(String name);
    void refreshCache();
}
