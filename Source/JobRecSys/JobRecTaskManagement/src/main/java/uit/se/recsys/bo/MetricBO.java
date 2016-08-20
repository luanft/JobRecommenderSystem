package uit.se.recsys.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.se.recsys.bean.MetricBean;
import uit.se.recsys.dao.MetricDAO;

@Service
public class MetricBO {
    @Autowired
    MetricDAO metricDAO;

    public List<MetricBean> getAllMetrics() {
	return metricDAO.getAllMetrics();
    }
}
