package in.nareshit.raghu.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nareshit.raghu.entity.SlotRequest;

public class InvoicePdfView extends AbstractPdfView{
	
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		SlotRequest sr = (SlotRequest) model.get("slots");
		
		HeaderFooter header = new HeaderFooter(
				new Phrase(sr.getPatient().getFirstName()+" "+
						sr.getPatient().getLastName()+"--"+System.currentTimeMillis()), 
				false);
		header.setAlignment(Element.ALIGN_RIGHT);
		document.setHeader(header);
		
		HeaderFooter footer = new HeaderFooter(
				new Phrase(new Date()+" (c)NareshIT, Page: "), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Font titleFont = new Font(Font.COURIER, 40, Font.BOLD, Color.BLUE);
		Paragraph title = new Paragraph("NARESH IT HOSPITAL", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		
		Font addressFont = new Font(Font.COURIER, 15, Font.ITALIC, Color.BLACK);
		Paragraph address = new Paragraph("Ameerpet, Hyd-560003", addressFont);
		address.setAlignment(Element.ALIGN_CENTER);
		document.add(address);
		
		Image img = Image
				.getInstance("https://www.nareshit.com/wp-content/uploads/2019/06/Nareshit-Logo-Png.png");
		
		img.setAlignment(Element.ALIGN_CENTER);
		img.setSpacingAfter(10f);
		document.add(img);
		
		SlotRequest sr = (SlotRequest) model.get("slots");
		
		
		String date = sr.getAppointment().getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		
		double fee = sr.getAppointment().getFee();
		double gst = fee * 6/100.0;
		double finalAmount = fee + 2*gst;
		
		PdfPTable table = new PdfPTable(4);
//		table.getDefaultCell().setBorderWidth(0f);
		table.setSpacingBefore(35.0f);
		table.setSpacingAfter(15.0f);
		table.addCell(getDesignCell("Appointment Date"));
		table.addCell(getTextCell(date));
		table.addCell(getDesignCell("Patient Name:"));
		table.addCell(getTextCell(
				sr.getPatient().getFirstName()+" "+sr.getPatient().getLastName()));
		table.addCell(getDesignCell("Doctor Name:"));
		table.addCell(getTextCell(
				sr.getAppointment().getDoctor().getFirstName()+" "+
					sr.getAppointment().getDoctor().getLastName()));
		table.addCell(getDesignCell("Final Ammount: "));
		table.addCell(getTextCell(
				String.valueOf(finalAmount)));
		
		document.add(table);
		
		
		PdfPTable billData = new PdfPTable(2);
		billData.getDefaultCell().setBorderWidth(0f);
		billData.setHorizontalAlignment(Element.ALIGN_RIGHT);
		billData.setSpacingBefore(30.0f);
		billData.setSpacingAfter(35.0f);
		billData.addCell("Base Amount");
		billData.addCell(String.valueOf(fee));
		billData.addCell("CGST");
		billData.addCell(String.valueOf(gst));
		billData.addCell("SGST");
		billData.addCell(String.valueOf(gst));
		billData.addCell("Total Amount");
		billData.addCell(String.valueOf(finalAmount));
		
		document.add(billData);
		
		Font noteFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.RED);
		Paragraph note = new Paragraph("NOTE: THIS IS AUTO-GENERATED PAYMENT SLIP, FOR MORE DETAILS CONTACT FRONTDESK @ 1234567890", noteFont);
		
		document.add(note);
		
		Font signFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD, Color.BLACK);
		Paragraph sign = new Paragraph("SIGNATURE", signFont);
		sign.setAlignment(Element.ALIGN_RIGHT);
		
		sign.setSpacingBefore(45.0f);
		sign.setSpacingAfter(10.0f);
		
		document.add(sign);
		
		Paragraph datePar = new Paragraph("Date: "+ new Date());
		datePar.setAlignment(Element.ALIGN_RIGHT);
		
		document.add(datePar);
		
	}
	
	private Phrase getDesignCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLUE);
		return new Phrase(data, font);
	}
	private Phrase getTextCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);
		return new Phrase(data, font);
	}

}
