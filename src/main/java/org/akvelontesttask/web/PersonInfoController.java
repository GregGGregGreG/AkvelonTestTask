package org.akvelontesttask.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.akvelontesttask.domain.PersonInfo;
import org.akvelontesttask.dto.CheckBoxes;
import org.akvelontesttask.dto.PersonInfoForm;
import org.akvelontesttask.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller which services all operations performed on {@link PersonInfo}
 * @author baddev
 *
 */

@Controller
@RequestMapping("/")
public class PersonInfoController {

	@Autowired
	private PersonInfoService personInfoService;
	
	public PersonInfoService getPersonInfoService() {
		return personInfoService;
	}

	public void setPersonInfoService(PersonInfoService personInfoService) {
		this.personInfoService = personInfoService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(Model model) {
		model.addAttribute("checkBoxes", new CheckBoxes());
		model.addAttribute("personInfoList", getPersonInfoService().loadAll());
		return "main";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String proceedToEditOrAdd(@ModelAttribute("checkBoxes") CheckBoxes checkBoxes,
			RedirectAttributes redirectAttrs) {
		long cbCount = checkBoxes.getCheckedIds().length;
		if (cbCount == 0) {
			return "redirect:add";
		} else if (cbCount == 1) {
			Long id = Long.parseLong(checkBoxes.getCheckedIds()[0]);
			return "redirect:" + id + "/edit";
		}
		redirectAttrs.addFlashAttribute("errorMsg",
				"You have selected more than one row to edit. Please, select only one!");
		return "redirect:/";
	}

	@RequestMapping(value = "{personInfoId}/edit", method = RequestMethod.GET)
	public String getEditPageForEdit(@PathVariable("personInfoId") Long id, Model model) {
		if (!model.containsAttribute("personInfoForm")) {
			model.addAttribute("personInfoForm", getPersonInfoService().load(id));
		}
		model.addAttribute("title", "Edit Person Info");
		return "edit";
	}
	
	@RequestMapping(value = "{personInfoId}/save", method = RequestMethod.POST)
	public String updatePersonInfo(@PathVariable("personInfoId") Long id,
			@ModelAttribute("personInfoForm") @Valid PersonInfoForm personInfoForm,
			BindingResult result,
			RedirectAttributes attrs) {
		if(result.hasErrors()){
			attrs.addFlashAttribute("org.springframework.validation.BindingResult.personInfoForm", result);
		    attrs.addFlashAttribute("personInfoForm", personInfoForm);
			return "redirect:edit";
		}
		PersonInfo pi = personInfoForm.makePersonInfo();
		pi.setId(id);
		getPersonInfoService().addOrSave(pi);
		return "redirect:/";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String getEditPageForAdd(Model model) {
		if (!model.containsAttribute("personInfoForm")) {
			model.addAttribute("personInfoForm", new PersonInfoForm());
		}
		model.addAttribute("title", "Add New Person Info");
		return "edit";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addNewPersonInfo(@ModelAttribute("personInfoForm") @Valid PersonInfoForm personInfoForm,
			BindingResult result,
			RedirectAttributes attrs) {
		if(result.hasErrors()){
			attrs.addFlashAttribute("org.springframework.validation.BindingResult.personInfoForm", result);
		    attrs.addFlashAttribute("personInfoForm", personInfoForm);
			return "redirect:add";
		}
		getPersonInfoService().addOrSave(personInfoForm.makePersonInfo());
		return "redirect:/";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteSelected(@ModelAttribute("checkBoxes") CheckBoxes checkBoxes,
			Model model) {
		Integer cbCount = checkBoxes.getCheckedIds().length;
		if (cbCount > 0) {
			getPersonInfoService().delete(parseCheckBoxes(checkBoxes.getCheckedIds()));
		}
		return "redirect:/";
	}

	@RequestMapping(value = { "{personInfoId}/cancel", "cancel" }, method = RequestMethod.POST)
	public String proceedCancel() {
		return "redirect:/";
	}

	/**
	 * This method takes String values and makes parsing to Long values
	 * 
	 * @param values
	 *            are digits or numbers in String representation
	 * @return List of parsed Long values
	 */
	public List<Long> parseCheckBoxes(String[] values) {
		Long[] parsed = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			parsed[i] = Long.parseLong(values[i]);
		}
		return Arrays.asList(parsed);
	}

}
