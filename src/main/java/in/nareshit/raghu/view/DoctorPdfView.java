package in.nareshit.raghu.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nareshit.raghu.entity.Doctor;

public class DoctorPdfView extends AbstractPdfView{
	
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		
		HeaderFooter footer = new HeaderFooter(new Phrase(new Date()+" (c)Soumendra Das Page No: "), 
				true);
		document.setFooter(footer);
		
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLDITALIC, Color.BLUE);
		Paragraph title = new Paragraph("DOCTOR DATA", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(20.0f);
		document.add(title);
		
		@SuppressWarnings("unchecked")
		List<Doctor> doctors = (List<Doctor>) model.get("doctors");
		
		Font tableHead = new Font(Font.TIMES_ROMAN, 15, Font.BOLDITALIC, Color.LIGHT_GRAY);
		PdfPTable table = new PdfPTable(6);
		
		table.addCell(new Phrase("ID", tableHead));
		table.addCell(new Phrase("NAME", tableHead));
		table.addCell(new Phrase("SPECS", tableHead));
		table.addCell(new Phrase("GENDER", tableHead));
		table.addCell(new Phrase("EMAIL", tableHead));
		table.addCell(new Phrase("MOBILE", tableHead));
		
		for(Doctor doc : doctors) {
			table.addCell(doc.getId().toString());
			table.addCell(doc.getFirstName()+' '+doc.getLastName());
			table.addCell(doc.getSpecialization().getSpecName());
			table.addCell(doc.getGender());
			table.addCell(doc.getEmail());
			table.addCell(doc.getMobile());
			
		}
		
		document.add(table);
	}

}
