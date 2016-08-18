package uit.se.recsys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uit.se.recsys.bean.MetricBean;

@Repository
public class MetricDAO {
    @Autowired
    DataSource dataSource;
    public List<MetricBean> getAllMetrics(){
	List<MetricBean> metrics = new ArrayList<MetricBean>();
	String sql = "select * from metrics";
	try {
	    PreparedStatement stm = dataSource.getConnection().prepareStatement(sql);
	    ResultSet rs = stm.executeQuery();
	    MetricBean metric;
	    while(rs.next()){
		metric = new MetricBean();
		metric.setId(rs.getInt("MetricId"));
		metric.setName(rs.getString("MetricName"));		
		metrics.add(metric);
	    }
	    rs.close();
	    stm.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	return metrics;
    }
}
