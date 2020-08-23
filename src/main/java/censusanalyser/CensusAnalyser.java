package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public static int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaCensusCSV> censusCSVIterator = new OpenCSVBuilder()
                                                             .getCSVFileIterator(reader, IndiaCensusCSV.class);
            return getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_INCORRECT_HEADER);
        } catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }
    public static int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaStateCodeCSV> stateCSVIterator = new OpenCSVBuilder()
                                                           .getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_INCORRECT_HEADER);
        } catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private static <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }
}

