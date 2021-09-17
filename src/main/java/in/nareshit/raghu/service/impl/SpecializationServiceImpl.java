package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.exception.SpecializationNotFoundException;
import in.nareshit.raghu.repository.SpecializationRepository;
import in.nareshit.raghu.service.ISpecializationService;

@Service
public class SpecializationServiceImpl implements ISpecializationService {
	
	@Autowired
	private SpecializationRepository repo;

	@Override
	public Long saveSpecialization(Specialization spec) {
		
		spec = repo.save(spec);
		return spec.getId();
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		
		return repo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		repo.delete(getOneSpecialization(id));

	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		
		return repo.findById(id)
				.orElseThrow(()->new SpecializationNotFoundException(id+" is not found"));
	}

	@Override
	public String updateSpecialization(Specialization spec) {
		
		//first check id is valid or not
		Optional<Specialization> tempSpec = repo.findById(spec.getId());
		if(tempSpec.isPresent()) {
			repo.save(spec);
			return "Record ("+spec.getId()+") is updated";
		}
		
		throw new SpecializationNotFoundException(spec.getId()+" not found");
	}
	
	@Override
	public boolean isSpecCodeExist(String specCode) {
		// TODO Auto-generated method stub
		return repo.getSpecCodeCount(specCode) > 0;
	}
	
	@Override
	public boolean isSpecNameExist(String specName) {
		// TODO Auto-generated method stub
		return repo.getSpecNameCount(specName) > 0;
	}

}
