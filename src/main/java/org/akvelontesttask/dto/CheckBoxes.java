package org.akvelontesttask.dto;

/**
 * Holds identifiers which will be transferred to controller when form is submitted
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
