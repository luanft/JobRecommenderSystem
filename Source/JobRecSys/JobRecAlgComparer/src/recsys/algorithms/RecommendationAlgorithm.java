package recsys.algorithms;


public abstract class RecommendationAlgorithm {
		
	protected String inputDirectory;
	protected String outputDirectory;
	
	public void init()
	{
		
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
		
}
