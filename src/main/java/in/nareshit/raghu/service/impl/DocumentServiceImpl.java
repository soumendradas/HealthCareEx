package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Document;
import in.nareshit.raghu.exception.DocumentNotFoundExeception;
import in.nareshit.raghu.repository.DocumentRepository;
import in.nareshit.raghu.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {
	
	@Autowired
	private DocumentRepository repo;

	@Override
	public Long saveDocument(Document document) {
		
		return repo.save(document).getDocId();
	}

	@Override
	public List<Object[]> getDocumentIdAndName() {
		
		return repo.getIdAndNames();
	}

	@Override
	public void deleteDocument(Long id) {
		
		repo.delete(getDocumentById(id));
		
	}

	@Override
	public Document getDocumentById(Long id) {
		
		return repo.findById(id).orElseThrow(
				()->new DocumentNotFoundExeception("Document not found"));
	}

}
