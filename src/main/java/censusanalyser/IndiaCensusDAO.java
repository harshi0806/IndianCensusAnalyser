package censusanalyser;
public class IndiaCensusDAO {
    public String state;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String stateCode;

    public String stateId;
    public String usState;
    public int usPopulation;
    public int housingUnits;
    public float totalArea;
    public float waterArea;
    public float landArea;
    public float populationDensity;
    public float housingDensity;
    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        stateCode = indiaStateCodeCSV.stateCode;
    }

    public IndiaCensusDAO(USCensusCSV usCensusCSV) {
        stateId = usCensusCSV.stateId;
        usState = usCensusCSV.usState;
        usPopulation = usCensusCSV.usPopulation;
        housingUnits = usCensusCSV.housingUnits;
        totalArea = usCensusCSV.totalArea;
        waterArea = usCensusCSV.waterArea;
        landArea = usCensusCSV.landArea;
        populationDensity = usCensusCSV.populationDensity;
        housingDensity = usCensusCSV.housingDensity;
    }
}
