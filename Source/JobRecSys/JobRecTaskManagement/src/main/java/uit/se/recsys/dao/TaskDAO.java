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
import uit.se.recsys.bean.TaskBean;

@Repository
public class TaskDAO {
    @Autowired
    DataSource dataSource;

    public boolean addTask(TaskBean task) {
	String sql = "insert into task (UserId, TaskName, TimeCreate, Status, Algorithm, Dataset, TaskType, TestSize) values (?,?,?,?,?,?,?,?)";
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
	    statement.setInt(8, task.getTestSize());
	    if (statement.executeUpdate() > 0)
		return true;
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
		taskBeans.add(task);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return taskBeans;
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
		task.setTestSize(Integer.parseInt(rs.getString("TestSize")));
		task.setMetrics(getMetricOfTask(task.getTaskId()));
		taskBeans.add(task);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return taskBeans;
    }

    private List<MetricBean> getMetricOfTask(int taskId) {
	List<MetricBean> metrics = new ArrayList<MetricBean>();
	String sql = "select MetricName, Score from metrics, evaluation, task where task.TaskId = evaluation.TaskId and evaluation.MetricId = metrics.MetricId and task.TaskId = "
			+ taskId;
	try {
	    PreparedStatement stm = dataSource.getConnection()
			    .prepareStatement(sql);
	    ResultSet rs = stm.executeQuery();
	    MetricBean metric;
	    while (rs.next()) {
		metric = new MetricBean();
		metric.setName(rs.getString("MetricName"));
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
		task.setTestSize(Integer.parseInt(rs.getString("TestSize")));
		task.setMetrics(getMetricOfTask(task.getTaskId()));
	    }
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
