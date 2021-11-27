package in.nareshit.raghu.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.nareshit.raghu.entity.Document;
import in.nareshit.raghu.service.IDocumentService;

@Controller
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	private IDocumentService service;
	
	@GetMapping("/all")
	public String showDocuments(Model model) {
		model.addAttribute("idVal", System.currentTimeMillis());
		List<Object[]> list = service.getDocumentIdAndName();
		model.addAttribute("list", list);
		return "Document";
	}
	
	@PostMapping("upload")
	public String upload(@RequestParam Long docId,
			@RequestParam MultipartFile docOb) {
		
		try {
			Document document = new Document();
			
			document.setDocId(docId);
			document.setDocName(docOb.getOriginalFilename());
			document.setDocData(docOb.getBytes());
			service.saveDocument(document);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:all";
	}
	
	@GetMapping("/delete")
	public String deleteDocument(@RequestParam Long id) {
		try {
			service.deleteDocument(id);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	@GetMapping("/download")
	public void downloadDocument(@RequestParam Long id,
			HttpServletResponse response
			) {
		try {
			//1. fetch DB object
			Document doc = service.getDocumentById(id);
			
			//2. provide file name using header
			response.setHeader("Content-Disposition",
					"attachment;filename="+doc.getDocName());
			//3. copy data from Doc to Response object
			//from -- to
			
			FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
