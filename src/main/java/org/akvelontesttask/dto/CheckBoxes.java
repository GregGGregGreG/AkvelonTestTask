package org.akvelontesttask.dto;

/**
 * Class for transfer objects which hold checkboxes
 * @author baddev
 *
 */
public class CheckBoxes {
	
	private String [] checkedIds = new String [] {};

	public String [] getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String [] checkedIds) {
		this.checkedIds = checkedIds;
	}
	
}
