package com.test.demo.app.dto;

/**
 * Project: ExerciseTimer
 * Package: com.exercise.app
 * Author: mason
 * Time: 13:54
 * Date: 2018-03-09
 * Created with IntelliJ IDEA
 */
public class CountryDto {

    private long id;
    private String countryname;
    private String countrycode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
