package data.cities;

public enum RussiaCityData implements ICityData {

    MOSCOW("Москва", CountriesData.RUSSIA),
    NORILSK("Норильск", CountriesData.RUSSIA),
    ST_PETRBURG("Санкт-Петербург", CountriesData.RUSSIA);

    private String nameCity;
    private CountriesData countriesData;

    RussiaCityData(String nameCity, CountriesData countriesData) {
        this.nameCity = nameCity;
        this.countriesData = countriesData;
    }

    public String getName() {
        return nameCity;
    }

    public CountriesData getCountriesData() {
        return countriesData;
    }
}
