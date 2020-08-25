package censusanalyser;

import com.google.gson.Gson;
import customcsv.util.CSVBuilderException;
import customcsv.util.CSVBuilderFactory;
import customcsv.util.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String, CensusDAO> censusMap = new HashMap<String, CensusDAO>();

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        censusMap= new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        return censusMap.size();
    }
    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> censusCSVIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(),false)
                         .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            return this.censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_INCORRECT_HEADER);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        censusMap= new CensusLoader().loadCensusData(csvFilePath, USCensusCSV.class);
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

