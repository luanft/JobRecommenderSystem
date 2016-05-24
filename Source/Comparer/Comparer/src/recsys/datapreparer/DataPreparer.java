package recsys.datapreparer;

public class DataPreparer {
	public static void main(String []args) {
		MysqlConnection c = new MysqlConnection();
		if(c.connect()){
			System.out.print("ok");
		}else
			System.out.print("fail");
	}
}
