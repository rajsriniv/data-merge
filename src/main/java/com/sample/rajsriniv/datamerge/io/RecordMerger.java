package com.sample.rajsriniv.datamerge.io;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Routine to merge files.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public void mergeFiles(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.

		//Define a list of data file readers
		//ASSUMPTIONS:
		//1. Only input filenames are passed as arguments
		//2. Data Files will contain a column called ID irrespective of language
		//3. HTML files with UTF-8 character encoding is supported
		List<DataFileReader> dataFileReaderList = new ArrayList<>();
		for (String arg : args) {
			//for each file, find the file type and create corresponding reader and add to the above list
			//To add support for a new file type, create a reader class which implements DataFileReader interface
			File file = new File(arg);
			if (!file.exists()) {
				System.err.println("File does not exist " + arg);
				System.exit(1);
			}
			String fileType = Files.probeContentType(file.toPath());
			switch (fileType) {
				case "text/html":
					dataFileReaderList.add(new HtmlReader(file));
					break;
				case "application/vnd.ms-excel":
					dataFileReaderList.add(new CsvReader(file));
					break;
				default:
					System.err.println("Unsupported file type " + arg);
					System.exit(1);
			}
		}
		//Pass the previously created data file reader list to combiner along with output file name
		new Combiner(dataFileReaderList, new File(FILENAME_COMBINED));
	}
}
