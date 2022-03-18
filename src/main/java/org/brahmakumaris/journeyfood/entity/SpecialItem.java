package org.brahmakumaris.journeyfood.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class SpecialItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer itemId;
	
	@NotEmpty(message = "Item is mandatory")
	private String item;
	
	public Integer getItemId() {
		return itemId;
	}
	
	public SpecialItem() {
		super();
	}

	public SpecialItem(String item) {
		super();
		this.item = item;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "SpecialItem [itemId=" + itemId + ", item=" + item + "]";
	}
}