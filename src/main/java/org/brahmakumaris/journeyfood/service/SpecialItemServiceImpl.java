package org.brahmakumaris.journeyfood.service;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.SpecialItem;
import org.brahmakumaris.journeyfood.repository.SpecialItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialItemServiceImpl implements SpecialItemService {
	SpecialItem item;
	@Autowired
	private SpecialItemRepository specialItemRepository;
	
	@Override
	public SpecialItem addItem(SpecialItem item) {
		this.item = new SpecialItem();
		this.item.setItem(item.getItem());
		return specialItemRepository.save(item);
	}

	@Override
	public List<SpecialItem> getItems() {
		return specialItemRepository.findAll();
	}

	@Override
	public SpecialItem getItemByItem(String item) {
		specialItemRepository.findByItem(item);
		return null;
	}

	@Override
	public void removeItem(SpecialItem item) {
		specialItemRepository.delete(item);

	}

	@Override
	public void update(SpecialItem item) {
		specialItemRepository.save(item);
	}

	@Override
	public SpecialItem getItem(int id) {
		return specialItemRepository.getOne(id);
	}

	@Override
	public void delete(int id) {
		specialItemRepository.deleteById(id);
	}

}
