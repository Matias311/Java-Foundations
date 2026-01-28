package com.javafoundations.app.semana3.salesanalytics.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.javafoundations.app.semana3.salesanalytics.exceptions.FileArgumentException;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for the class ParserCsv, test the mothd parse that reads a csv file")
public class ParserCsvTest {
  private ParserCsv parser;

  @BeforeEach
  void setUp() {
    this.parser = new ParserCsv();
  }

  @Test
  @DisplayName("Test pass to the parser a null reader")
  void nullReaderToParser() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    assertEquals("Reader can not be null", ex.getMessage());
  }

  @Test
  @DisplayName("Read a string (csv formated) and verify the lenght of the list that return")
  void readCsvStringVerifyLenghtListParse() {
    String csv =
        """

        S-1001,2026-01-01T10:15:00,P-01,Mouse,Accessories,2,19.99
        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,1,49.90
        """;
    StringReader reader = new StringReader(csv);
    List<SaleRecord> list = parser.parse(reader);
    assertEquals(2, list.size()); // start at 0 but add 1 because the header
  }

  @Test
  @DisplayName("Try to parse a incorrect date time should throw a FileArgumentException")
  void tryToParseDateTime() {
    String csv =
        """

        S-1001,bad-date,P-01,Mouse,Accessories,2,19.99
        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,1,49.90
        """;
    StringReader reader = new StringReader(csv);
    Exception ex = assertThrows(FileArgumentException.class, () -> parser.parse(reader));
    assertEquals("Invalid date", ex.getMessage());
  }

  @Test
  @DisplayName("Try to parse a incorrect quantity should throw FIleParseException")
  void tryToParseQuantity() {
    String csv =
        """

        S-1001,2026-01-01T10:15:00,P-01,Mouse,Accessories,2,19.99
        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,bad-quantity,49.90
        """;
    StringReader reader = new StringReader(csv);
    Exception ex = assertThrows(FileArgumentException.class, () -> parser.parse(reader));
    assertEquals("Invalid number", ex.getMessage());
  }

  @Test
  @DisplayName("Try to parse a incorrect unit price should throw FIleParseException")
  void tryToParseUnitPrice() {
    String csv =
        """

        S-1001,2026-01-01T10:15:00,P-01,Mouse,Accessories,2,19.99
        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,1,bad-unitPrice
        """;
    StringReader reader = new StringReader(csv);
    Exception ex = assertThrows(FileArgumentException.class, () -> parser.parse(reader));
    assertEquals("Invalid number", ex.getMessage());
  }

  @Test
  @DisplayName("Try to create sale record with a csv with empty spaces")
  void tryCreateSaleRecordEmptySpaces() {
    String csv =
        """



        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,1,19.11
        """;
    StringReader reader = new StringReader(csv);
    List<SaleRecord> list = parser.parse(reader);
    int size = list.size();
    assertEquals(1, list.size(), () -> "Error the size is: " + size);
  }

  @Test
  @DisplayName("Verify the csv data, should be more than 7 values (6 in index)")
  void verifyDataCsvLenght() {
    String csv =
        """

        S-1001,2026-01-01T10:15:00,P-01,Mouse,Accessories,2
        S-1002,2026-01-01T12:40:00,P-02,Keyboard,Accessories,1,bad-unitPrice
        """;
    StringReader reader = new StringReader(csv);
    Exception ex = assertThrows(FileArgumentException.class, () -> parser.parse(reader));
    assertEquals("Invalid data. Should have all the values", ex.getMessage());
  }
}
