package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.SpecialItemForADate;
import org.brahmakumaris.journeyfood.order.web.AddSpecialItems;
import org.brahmakumaris.journeyfood.repository.SpecialItemForADateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialItemForADateServiceImpl implements SpecialItemForADateService {
	
	private SpecialItemForADate specialItemForADate;
	@Autowired
	private SpecialItemForADateRepository specialItemForADateRepository;

	@Override
	public SpecialItemForADate addItem(AddSpecialItems item) {
		this.specialItemForADate = new SpecialItemForADate();
		this.specialItemForADate.setServingSlot(item.getServingSlot());
		this.specialItemForADate.setSpecialItems(item.getSpecialItems());
		this.specialItemForADate.setServingDate(item.getServingDate());
		this.specialItemForADate.setDateOfOrder(item.getDateOfOrder());
		return specialItemForADateRepository.save(specialItemForADate);
	}

	@Override
	public List<SpecialItemForADate> getItems() {
		return specialItemForADateRepository.findAll();
	}

	@Override
	public SpecialItemForADate getItemsByServingDate(LocalDate date) {
		SpecialItemForADate specialItemForADate = null;
		try {
			specialItemForADate = specialItemForADateRepository.findByServingDate(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return specialItemForADate;
	}

	@Override
	public List<SpecialItemForADate> getItemsByDateOfOrder(LocalDate date) {
		return specialItemForADateRepository.findByDateOfOrder(date);
	}

	@Override
	public List<SpecialItemForADate> getItemsByServingDateAndServingSlot(LocalDate date, String slot) {
		return specialItemForADateRepository.findByServingDateAndServingSlot(date, slot);
	}

	@Override
	public List<SpecialItemForADate> getItemsByServingSlot(String slot) {
		return specialItemForADateRepository.findByServingSlot(slot);
	}

	@Override
	public void removeItem(AddSpecialItems item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AddSpecialItems item) {
		this.specialItemForADate = getItem(item.getId());
		this.specialItemForADate.setServingSlot(item.getServingSlot());
		this.specialItemForADate.setSpecialItems(item.getSpecialItems());
		this.specialItemForADate.setServingDate(item.getServingDate());
		specialItemForADateRepository.save(specialItemForADate);

	}

	@Override
	public SpecialItemForADate getItem(long id) {
		return specialItemForADateRepository.getOne(id);
	}

	@Override
	public void delete(long id) {
		specialItemForADateRepository.deleteById(id);
	}

}
