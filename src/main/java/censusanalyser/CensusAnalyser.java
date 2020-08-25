package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDAO> censusMap = new HashMap<String, CensusDAO>();

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        return censusMap.size();
    }
    public int loadUSCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap= new CensusLoader().loadCensusData(USCensusCSV.class, csvFilePath);
        return censusMap.size();
    }
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = sort(censusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getStatePopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(censusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getStatePopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(censusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getStateAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(censusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getStateCodeWiseSortedData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = sort(stateCodeComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getUSCensusPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> usCensusComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(usCensusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getUSCensusPopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> usCensusComparator = Comparator.comparing(census -> census.populationDensity);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(usCensusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    public String getUSCensusAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusMap  == null || censusMap .size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> usCensusComparator = Comparator.comparing(census -> census.totalArea);
        List<CensusDAO> censusDAOList = censusMap.values().stream().collect(Collectors.toList());
        censusDAOList = descendingSort(usCensusComparator, censusDAOList);
        return new Gson().toJson(censusDAOList);
    }
    private static <E> List<E> sort(Comparator<E> censusComparator, List<E> censusList) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for (int j =0; j< censusList.size() -i -1; j++) {
                E census1 = censusList.get(j);
                E census2 = censusList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
        return censusList;
    }
    private static <E> List<E> descendingSort(Comparator<E> censusComparator, List<E> censusList) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for (int j =0; j< censusList.size() -i -1; j++) {
                E census1 = censusList.get(j);
                E census2 = censusList.get(j+1);
                if (censusComparator.compare(census1, census2) < 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
        return censusList;
    }
}

