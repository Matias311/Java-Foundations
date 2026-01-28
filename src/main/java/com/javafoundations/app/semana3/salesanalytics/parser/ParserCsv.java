package com.javafoundations.app.semana3.salesanalytics.parser;

import com.javafoundations.app.semana3.salesanalytics.exceptions.FileArgumentException;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ParserCsv {

  /**
   * Read a CsvFile and creates a SaleRecord. If the file couldnt be read throws a IOException, when
   * creates a sale record and can not parse the date Throws a DateTimeParseException or if can not
   * parse the int or big decimal throws a NumberFormatException.
   *
   * @param reader Reader
   * @return List of SaleRecord
   */
  public List<SaleRecord> parse(Reader reader) {
    if (reader == null) {
      throw new IllegalArgumentException("Reader can not be null");
    }
    List<SaleRecord> list = new ArrayList<>();

    try (BufferedReader read = new BufferedReader(reader)) {
      // skip the first line
      read.readLine();

      String line;
      while ((line = read.readLine()) != null) {
        if (line.isBlank()) {
          continue;
        }

        String[] info = line.split(",");

        list.add(
            SaleRecord.create(
                info[0],
                LocalDateTime.parse(info[1]),
                info[2],
                info[3],
                info[4],
                Integer.parseInt(info[5]),
                new BigDecimal(info[6])));
      }
    } catch (IOException ioex) {
      System.err.println("It can not read the file: " + ioex);
    } catch (DateTimeParseException dateEx) {
      throw new FileArgumentException("Invalid date", dateEx);
    } catch (NumberFormatException numberEx) {
      throw new FileArgumentException("Invalid number", numberEx);
    } catch (ArrayIndexOutOfBoundsException arrEx) {
      throw new FileArgumentException("Invalid data. Should have all the values", arrEx);
    }
    return list;
  }
}
