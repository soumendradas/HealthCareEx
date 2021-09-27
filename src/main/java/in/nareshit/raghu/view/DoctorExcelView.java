package in.nareshit.raghu.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.nareshit.raghu.entity.Doctor;

public class DoctorExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//set your file name
		response.addHeader("Content-Disposition", "attachment;filename=Doctor.xlsx");
		
		
		//get all doctors throw model memory
		@SuppressWarnings("unchecked")
		List<Doctor> doctors = (List<Doctor>) model.get("doctors");
		
		Sheet sheet = workbook.createSheet("DoctorData");
		setHead(sheet);
		setBody(sheet, doctors);
	}

	private void setBody(Sheet sheet, List<Doctor> doctors) {
		int rowNum = 1;
		for(Doctor doc : doctors) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(doc.getId());
			row.createCell(1).setCellValue(doc.getFirstName()+" "+doc.getLastName());
			row.createCell(2).setCellValue(doc.getSpecialization().getSpecName());
			row.createCell(3).setCellValue(doc.getEmail());
			row.createCell(4).setCellValue(doc.getMobile());
			row.createCell(5).setCellValue(doc.getGender());
			row.createCell(6).setCellValue(doc.getAddress());
			row.createCell(7).setCellValue(doc.getNote());
		}
		
	}

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Specialization");
		row.createCell(3).setCellValue("Email");
		row.createCell(4).setCellValue("Mobile");
		row.createCell(5).setCellValue("Gender");
		row.createCell(6).setCellValue("Address");
		row.createCell(7).setCellValue("Notes");
		
	}

}
