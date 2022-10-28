package com.bentley.constructhelix.licensing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class LicensingTestApp {

	private static final Logger LOG = Logger.getLogger(LicensingTestApp.class.getName());
	
	public static void main(String[] args) {

		try {

			LOG.info("Starting reading input file");
			String fileName = "d:\\deleteme\\projectguids\\projectguids5.csv";

			FileReader csvReader = new FileReader(fileName);
			FileWriter csvWriter = new FileWriter(fileName + "_generatedKeys.csv", true);
			csvWriter.append("----------------------------" + new Date() + "-------------------------------\n");
			LOG.info("processing file");
			BufferedReader bfr = new BufferedReader(csvReader);
			String currLine;
			String[] tokens;
			while ((currLine = bfr.readLine()) != null) {

				if (currLine.startsWith("#") == false) {

					tokens = currLine.split("\t");

					if (tokens.length >= 4) {
						String originalKeyVersion = "nPulseKeyV1";
						String originalName = tokens[0];
						String originalProjId = tokens[1];
						String originalStart = tokens[2];
						String originalEnd = tokens[3];

						{
							String EncryptedKey = LicenseManager.generateProjectLicenseKey(originalProjId,
									originalStart, originalEnd, originalKeyVersion);
							LOG.info("Generating License for " + originalName + " ...");
							csvWriter.append(tokens[0]);
							csvWriter.append("\t");
							csvWriter.append(tokens[1]);
							csvWriter.append("\t");
							csvWriter.append(tokens[2]);
							csvWriter.append("\t");
							csvWriter.append(tokens[3]);
							csvWriter.append("\t");
							csvWriter.append(EncryptedKey);
							csvWriter.append("\n");
						}
					}
				}
			}
			csvWriter.append("-----------------------------------------------------------\n");
			csvWriter.flush();
			csvWriter.close();
			bfr.close();
			LOG.info("Finished Processing file");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOG.info("Caught error:" + e.getMessage());
		}
	}

}
