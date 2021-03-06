package uit.se.recsys.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uit.se.recsys.bean.MetricBean;
import uit.se.recsys.bean.TaskBean;
import uit.se.recsys.utils.DatasetUtil;

@Repository
public class TaskDAO {
    @Autowired
    DataSource dataSource;
    @Autowired
    DatasetUtil datasetUtil;

    public boolean addTask(TaskBean task) {
	String sql = "insert into task (UserId, TaskName, TimeCreate, Status, Algorithm, Dataset, TaskType, EvaluationType, EvaluationParam) values (?,?,?,?,?,?,?,?,?)";
	try {
	    PreparedStatement statement = dataSource.getConnection()
			    .prepareStatement(sql);
	    statement.setInt(1, task.getUserId());
	    statement.setString(2, task.getTaskName());
	    statement.setTimestamp(3, task.getTimeCreate());
	    statement.setString(4, task.getStatus());
	    statement.setString(5, task.getAlgorithm());
	    statement.setString(6, task.getDataset());
	    statement.setString(7, task.getType());
	    statement.setString(8, task.getEvaluationType());
	    statement.setInt(9, task.getEvaluationParam());
	    if (statement.executeUpdate() > 0){
		statement.close();
		return true;
	    }		
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public List<TaskBean> getAllRecommendationTasks() {
	String sql = "select * from task where TaskType = 'rec'";
	List<TaskBean> taskBeans = new ArrayList<TaskBean>();
	try {
	    PreparedStatement statement = dataSource.getConnection()
			    .prepareStatement(sql);
	    ResultSet rs = statement.executeQuery();
	    while (rs.next()) {
		TaskBean task = new TaskBean();
		task.setTaskId(rs.getInt("TaskId"));
		task.setUserId(rs.getInt("UserId"));
		task.setAlgorithm(rs.getString("Algorithm"));
		task.setDataset(rs.getString("Dataset"));
		task.setStatus(rs.getString("Status"));
		task.setTaskName(rs.getString("TaskName"));
		task.setTimeCreate(rs.getTimestamp("TimeCreate"));
		task.setType(rs.getString("TaskType"));
		task.setConfig(readConfig(task));
		taskBeans.add(task);
	    }
	    rs.close();
	    statement.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return taskBeans;
    }

    private Properties readConfig(TaskBean task) {
	Properties config = new Properties();
	try {
	    config.load(new FileInputStream(datasetUtil.getOutputLocation(task)
			    + "config.properties"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return config;
    }

    public List<TaskBean> getAllEvaluationTasks() {
	String sql = "select * from task where TaskType = 'eval'";
	List<TaskBean> taskBeans = new ArrayList<TaskBean>();
	try {
	    PreparedStatement statement = dataSource.getConnection()
			    .prepareStatement(sql);
	    ResultSet rs = statement.executeQuery();
	    while (rs.next()) {
		TaskBean task = new TaskBean();
		task.setTaskId(rs.getInt("TaskId"));
		task.setUserId(rs.getInt("UserId"));
		task.setAlgorithm(rs.getString("Algorithm"));
		task.setDataset(rs.getString("Dataset"));
		task.setStatus(rs.getString("Status"));
		task.setTaskName(rs.getString("TaskName"));
		task.setTimeCreate(rs.getTimestamp("TimeCreate"));
		task.setType(rs.getString("TaskType"));
		task.setEvaluationParam(Integer
				.parseInt(rs.getString("EvaluationParam")));
		task.setMetrics(getMetricOfTask(task.getTaskId()));
		task.setEvaluationType(rs.getString("EvaluationType"));
		task.setConfig(readConfig(task));
		taskBeans.add(task);
	    }
	    rs.close();
	    statement.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return taskBeans;
    }

    private List<MetricBean> getMetricOfTask(int taskId) {
	List<MetricBean> metrics = new ArrayList<MetricBean>();
	String sql = "select Metric, Score from evaluation, task where task.TaskId = evaluation.TaskId and task.TaskId = "
			+ taskId;
	try {
	    PreparedStatement stm = dataSource.getConnection()
			    .prepareStatement(sql);
	    ResultSet rs = stm.executeQuery();
	    MetricBean metric;
	    while (rs.next()) {
		metric = new MetricBean();
		metric.setName(rs.getString("Metric"));
		metric.setScore(rs.getFloat("Score"));
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

    public TaskBean getTaskById(int id) {
	String sql = "select * from task where TaskId = ?";
	TaskBean task = new TaskBean();
	try {
	    PreparedStatement statement = dataSource.getConnection()
			    .prepareStatement(sql);
	    statement.setInt(1, id);
	    ResultSet rs = statement.executeQuery();
	    while (rs.next()) {
		task.setTaskId(rs.getInt("TaskId"));
		task.setUserId(rs.getInt("UserId"));
		task.setAlgorithm(rs.getString("Algorithm"));
		task.setDataset(rs.getString("Dataset"));
		task.setStatus(rs.getString("Status"));
		task.setTaskName(rs.getString("TaskName"));
		task.setTimeCreate(rs.getTimestamp("TimeCreate"));
		task.setType(rs.getString("TaskType"));
		task.setConfig(readConfig(task));
		task.setMetrics(getMetricOfTask(task.getTaskId()));
	    }
	    rs.close();
	    statement.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return task;
    }

    public int generateId() {
	int maxId = 1;
	try {
	    PreparedStatement stm = dataSource.getConnection().prepareStatement(
			    "select max(TaskId) as maxId from task");
	    ResultSet rs = stm.executeQuery();
	    if (rs.next()) {
		maxId = rs.getInt("maxId");
		rs.close();
		stm.close();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return maxId;
    }
}
