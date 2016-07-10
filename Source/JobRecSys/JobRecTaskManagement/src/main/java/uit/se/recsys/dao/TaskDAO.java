package uit.se.recsys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uit.se.recsys.bean.TaskBean;

@Repository
public class TaskDAO {
    @Autowired
    DataSource dataSource;

    public boolean addTask(TaskBean task) {
	String sql = "insert into task (UserId, TaskName, TimeCreate, Status, Algorithm, Dataset) values (?,?,?,?,?,?)";
	try {
	    PreparedStatement statement = dataSource.getConnection()
			    .prepareStatement(sql);
	    statement.setInt(1, task.getUserId());
	    statement.setString(2, task.getTaskName());
	    statement.setTimestamp(3, task.getTimeCreate());
	    statement.setString(4, task.getStatus());
	    statement.setString(5, task.getAlgorithm());
	    statement.setString(6, task.getDataset());
	    if (statement.executeUpdate() > 0)
		return true;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public List<TaskBean> getAllTasks() {
	String sql = "select * from task";
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
		taskBeans.add(task);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return taskBeans;
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
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return task;
    }
}
