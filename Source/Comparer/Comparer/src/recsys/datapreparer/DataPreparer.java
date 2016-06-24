package recsys.datapreparer;

public class DataPreparer {				
	protected DataSetReader dataReader = null;
	public DataPreparer(String dir)
	{
		dataReader = new DataSetReader(dir);
	}
}