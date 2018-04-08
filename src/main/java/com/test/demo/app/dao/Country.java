package com.test.demo.app.dao;

import com.test.demo.app.dto.CountryDto;

/**
 * Project: ExerciseTimer
 * Package: com.exercise.app.dao
 * Author: mason
 * Time: 11:33
 * Date: 2018-03-29
 * Created with IntelliJ IDEA
 */
public interface Country {

    CountryDto selectCountry(Long id);

}
