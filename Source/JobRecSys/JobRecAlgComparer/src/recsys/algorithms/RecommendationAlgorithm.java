package recsys.algorithms;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class RecommendationAlgorithm {

	protected String inputDirectory;
	protected String outputDirectory;
	protected String testDirectory;
	protected Properties config;
	protected boolean useConfig;

	public RecommendationAlgorithm(){}
	
	public void init(){}
	
	public RecommendationAlgorithm(String input, String output, boolean useConfig){
		this.inputDirectory = input;
		this.outputDirectory = output;
		this.testDirectory = "";
		this.config = new Properties();
		this.useConfig = useConfig;
	}
	
	public RecommendationAlgorithm(String input, String output, String test, boolean useConfig){
		this.inputDirectory = input;
		this.outputDirectory = output;
		this.testDirectory = test;
		this.config = new Properties();
		this.useConfig = useConfig;
	}
	
	public String getInputDirectory() {
		return inputDirectory;
	}

	public void setInputDirectory(String inputDirectory) {
		this.inputDirectory = inputDirectory;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	protected void readConfiguration(String fileLocation, boolean useConfig) {
		if (useConfig) {
			try {
				config.load(new FileInputStream(fileLocation));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			/*cf configuration*/
			config.setProperty("cf.type", "UserBase");
			config.setProperty("cf.similarity", "PEARSON_CORRELATION");
			config.setProperty("cf.neighborhood.type", "NearestNUserNeighborhood");
			config.setProperty("cf.neighborhood.param", "10");
			config.setProperty("cf.recommendItems", "10");
			
			/*cb configuration*/
			
			/*hb configuration*/
		}
	}
}
