import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class LoadMoviesDB {

	private static final String PATH_DATA_SQL = "../java/texo/src/main/resources/data.sql";
	private static final String PATH_CSV = "movielist.csv";
	private static final String TEMPLATE_INSERT = "INSERT INTO awards (year,title,studios,producer,winner) VALUES (";

	private static final int FIRST_LINE = 1;
	private static final String INSERT_END = "');";
	private static final String INSERT_DIVISION_2 = "', '";
	private static final String INSERT_DIVISION = ", '";
	private static final int LAST_POSITION = 4;
	private static final int LAST_ELEMENT = 5;
	private static final int TOTAL_ELEMENTS = LAST_ELEMENT;
	private static final String COMMA = ",";
	private static final String CSV_REPLACE_PRODUCER_2 = " and ";
	private static final String CSV_REPLACE_PRODUCER_1 = ", and";
	private static final char WINNER_VALUE_YES = 'Y';
	private static final char WINNER_VALUE_NO = 'N';
	private static final String CSV_WINNER_YES = "yes";
	private static final String CSV_SEPARATOR = ";";

	public static void main(String[] args) {
		try {	
			new LoadMoviesDB().generateDataSQL();
		} catch (final URISyntaxException | IOException e) {
			System.err.println("Problem to load movie list (file movielist.csv of resource)" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void generateDataSQL() throws URISyntaxException, IOException {
		String[] line;
		Character winner;
		String auxProducer;
		String[] producers;
		StringBuilder insertSQL = new StringBuilder();
		Integer countInserts = 0, reads = 1;

		final FileWriter fw = new FileWriter(PATH_DATA_SQL);
		final BufferedWriter writer = new BufferedWriter(fw);
		final File fileCSV = new File(PATH_CSV);
		final List<String> rows = Files.readAllLines(fileCSV.toPath(), StandardCharsets.UTF_8);

		System.out.println("rows " + rows.size());

		for (String row : rows) {
			if (reads != FIRST_LINE) { // ignore description line
				row = row.replaceAll("'", "''");
				line = row.split(CSV_SEPARATOR);
				winner = WINNER_VALUE_NO;
				if (line.length == TOTAL_ELEMENTS && line[LAST_POSITION].equals(CSV_WINNER_YES)) {
					winner = WINNER_VALUE_YES;
				}
				auxProducer = line[3].replaceAll(CSV_REPLACE_PRODUCER_1, COMMA).replaceAll(CSV_REPLACE_PRODUCER_2,
						COMMA);
				producers = auxProducer.split(COMMA);
				for (final String producer : producers) {
					insertSQL = insertSQL.append(TEMPLATE_INSERT).append(line[0]).append(INSERT_DIVISION)
							.append(line[1].strip()).append(INSERT_DIVISION_2).append(line[2].strip())
							.append(INSERT_DIVISION_2).append(producer.strip()).append(INSERT_DIVISION_2).append(winner)
							.append(INSERT_END);
					writer.write(insertSQL.toString());
					writer.newLine();
					insertSQL.setLength(0);
					countInserts++;
				}
			}
			reads++;
		}	
		writer.close();

		System.out.println("Generated " + countInserts + " inserts in " + PATH_DATA_SQL + " from " + PATH_CSV);
	}
}
