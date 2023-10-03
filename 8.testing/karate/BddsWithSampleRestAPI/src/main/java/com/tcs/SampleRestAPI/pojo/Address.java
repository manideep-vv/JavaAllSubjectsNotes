package com.tcs.SampleRestAPI.pojo;

public class Address {
    String cityname;
    String countryName;

    @Override
    public String toString() {
        return "Address{" +
                "cityname='" + cityname + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    public Address(String cityname, String countryName) {
        this.cityname = cityname;
        this.countryName = countryName;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
