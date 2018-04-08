package com.test.demo.app.dao;

import com.test.demo.app.dto.CountryDto;

public interface CountryDao {

    CountryDto selectCountry(Long id);

}
