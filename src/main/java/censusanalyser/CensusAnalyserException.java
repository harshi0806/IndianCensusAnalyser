package censusanalyser;

public class CensusAnalyserException extends Exception {
    enum ExceptionType{
        CENSUS_FILE_PROBLEM, CENSUS_INCORRECT_HEADER
    }
    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
