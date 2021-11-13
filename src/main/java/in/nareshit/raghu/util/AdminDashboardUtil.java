package in.nareshit.raghu.util;

import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class AdminDashboardUtil {
	
	public void generatePie(String path, List<Object[]> list) {
		//1. create dataset using list
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Object[] ob: list) {
			//key-label, val-data/count
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}
		//2. create JFreeChart using DataSet and ChartFactory
				//title, dataset
		
		JFreeChart chart = ChartFactory.createPieChart("ADMIN SLOTS DATA", dataset);
		
		//3. convert as a image
		try {
			ChartUtils.saveChartAsJPEG(new File(path+"/adminA.jpg"), chart, 300,300);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generateBar(String path, List<Object[]> list) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] ob: list) {
			dataset.setValue(Double.valueOf(ob[1].toString()),ob[0].toString(),"");
		}
		
		JFreeChart chart = ChartFactory.createBarChart("ADMIN SLOTS DATA", 
				"SLOTS", "COUNT", dataset);
		
		try {
			ChartUtils.saveChartAsJPEG(new File(path+"/adminB.jpg"), chart, 300, 300);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
