package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "IndiaStateCensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "IndiaStateCensusData/csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_TYPE = "IndiaStateCode.txt";
    private static final String WRONG_STATE_CSV_FILE_DELIMITER = "IndiaStateCode/csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    //This test case is used to ensure number of record matches from State Census CSV file
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }
    //This test case checks for StateCensusCSV file if incorrect returns a Custom Exception
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks when path correct but type incorrect throws Custom Exception
    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks when path correct but delimiter incorrect throws Custom Exception
    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks for StateCensusCSV file Header
    @Test
    public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_INCORRECT_HEADER,e.type);
        }
    }
    // This test case checks for Sorted Census Data in a Json format according to State alphabetical order
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV =  new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case checks for Sorted Census Data in a Json Format according to most Populous state to least
    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStatePopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV =  new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case checks for Sorted Census Data in a Json Format according to most Populous state density to least
    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStatePopulationDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV =  new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case checks for Sorted Census Data in a Json Format according to most Populous state density to least
    @Test
    public void givenIndianCensusData_WhenSortedByStateArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV =  new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case is used to ensure number of record matches from State Code CSV file
    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int stateCodeData = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37,stateCodeData);
        } catch (CensusAnalyserException e) { }
    }
    //This test case checks for StateCodeCSV file if incorrect returns a Custom Exception
    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks when StateCodeCSV file path correct but type incorrect throws Custom Exception
    @Test
    public void givenIndiaStateCodeData_WithWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_STATE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks StateCodeCSV file path correct but delimiter incorrect throws Custom Exception
    @Test
    public void givenIndiaStateCodeData_WithWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_STATE_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //This test case checks for StateCodeCSV file Header
    @Test
    public void givenIndiaStateCodeData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_INCORRECT_HEADER,e.type);
        }
    }
    // This test case checks for Sorted State Data in a Json format according to State Code alphabetical order
    @Test
    public void givenIndiaStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CSV_FILE_PATH);
            String sortedStateData = censusAnalyser.getStateCodeWiseSortedData();
            IndiaStateCodeCSV[] stateCSV =  new Gson().fromJson(sortedStateData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD", stateCSV[0].stateCode);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case is used to ensure number of record matches from US Census CSV file
    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }
    //This test case checks for Sorted USCensus Data in a Json Format according to most Populous state to least
    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getUSCensusPopulationWiseSortedCensusData();
            USCensusCSV[] usCensusCSV =  new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", usCensusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case checks for Sorted USCensus Data in a Json Format according to most Populous Density state to least
    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getUSCensusPopulationDensityWiseSortedCensusData();
            USCensusCSV[] usCensusCSV =  new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", usCensusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    //This test case checks for Sorted USCensus Data in a Json Format according to most Populous Area state to least
    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String usCensusData = censusAnalyser.getUSCensusAreaWiseSortedCensusData();
            USCensusCSV[] usCensusCSV =  new Gson().fromJson(usCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", usCensusCSV[0].state);
        } catch (CensusAnalyserException e ) { }
    }
    @Test
    public void givenUSAndIndiaCensus_WhenSorted_ShouldReturnName() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
        String populousIndianState = censusAnalyser.getStatePopulationDensityWiseSortedCensusData();
        String populousUSState = censusAnalyser.getUSCensusPopulationDensityWiseSortedCensusData();
        USCensusCSV[] CensusUsCSV = new Gson().fromJson(populousUSState, USCensusCSV[].class);
        IndiaCensusCSV[] CensusIndiaCSV = new Gson().fromJson(populousIndianState, IndiaCensusCSV[].class);
        Assert.assertEquals("District of Columbia", CensusUsCSV[0].state);
        Assert.assertEquals("Bihar", CensusIndiaCSV[0].state);

    }
}
