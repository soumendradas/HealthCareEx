package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Document;

public interface IDocumentService {
	
	Long saveDocument(Document document);
	
	List<Object[]> getDocumentIdAndName();
	void deleteDocument(Long id);
	
	Document getDocumentById(Long id);

}
