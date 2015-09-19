$(document).ready(function() {

	var $checkboxes = $(':checkbox:not(#select_all)');
	var $deleteBtn = $('#delete_btn');
	var $reloadBtn = $('#refresh_btn')
	var $overlay = $('#popup_overlay');
	var $closePopupBtn = $('#close_btn');
	
	// when page is loaded
	$(function() {
		// uncheck boxes
		$checkboxes.prop('checked', false);
		// disable delete button
		$deleteBtn.prop("disabled", true);
		// uncheck #select_all box
		$('#select_all').prop('checked', false);
	});
	
	$reloadBtn.click(function() {
		location.reload();
	});
	
	//if error block contains error, show it and make corresponding error fields red
	$('span.error').each(function(){
	    if ($(this).text().trim().length>0) {
	        var $hiddenData = $('td').has(this);
	        $hiddenData.parent().find('input').addClass('red-bord');
	        $hiddenData.toggleClass('hidden');
	    }
	});
	
	$closePopupBtn.click(function(){
		$overlay.toggleClass('hidden');
	});

	// when clicked on some checkbox except #select_all
	$checkboxes.click(function() {
		// enable or disable delete button, depends on $checkboxes state
		$deleteBtn.prop("disabled", !$checkboxes.is(":checked"));
	});

	// when changed #select_all
	$('#select_all').change(function() {
		if ($(this).prop('checked')) {
			// check all boxes
			$checkboxes.prop('checked', true);
			// enable delete button
			$deleteBtn.prop("disabled", false);
		} else {
			// uncheck all boxes
			$checkboxes.prop('checked', false);
			// disable delete button
			$deleteBtn.prop("disabled", true);
		}
	});

});