package com.rays.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.MarksheetDTO;
import com.rays.dto.StudentDTO;
import com.rays.form.MarksheetForm;
import com.rays.service.MarksheetServiceInt;
import com.rays.service.StudentServiceInt;

/**
 * Marksheet Controller class.
 * 
 * This controller handles all HTTP requests related to Marksheet module. It
 * extends BaseCtl to inherit common CRUD operations such as: - Save - Delete -
 * Get by ID - Search with pagination
 * 
 * Additional functionalities: - Preload student list for dropdown - Find
 * marksheet by roll number - Fetch merit list
 * 
 * Endpoints are mapped under "/Marksheet".
 * 
 * @author Abhishish Bhawsar
 */
@RestController
@RequestMapping(value = "Marksheet")
public class MarksheetCtl extends BaseCtl<MarksheetForm, MarksheetDTO, MarksheetServiceInt> {

	/**
	 * Service for Student-related operations.
	 */
	@Autowired
	private StudentServiceInt studentService;

	/**
	 * Preload API to fetch student list for dropdown.
	 * 
	 * @return ORSResponse containing student list
	 */
	@GetMapping("preload")
	public ORSResponse preload() {
		ORSResponse res = new ORSResponse(true);
		List<StudentDTO> list = studentService.search(new StudentDTO(), userContext);
		res.addResult("studentList", list);
		return res;
	}

	/**
	 * Retrieves marksheet by roll number.
	 * 
	 * @param rollNo student roll number
	 * @return ORSResponse containing marksheet details
	 */
	@GetMapping("rollno/{rollNo}")
	public ORSResponse rollNo(@PathVariable String rollNo) {
		ORSResponse res = new ORSResponse(true);
		MarksheetDTO dto = baseService.findByRollNo(rollNo, userContext);
		if (dto != null) {
			res.addData(dto);
		} else {
			res.setSuccess(false);
			res.addMessage("Record not found");
		}
		return res;
	}

	/**
	 * Retrieves merit list of students.
	 * 
	 * @return ORSResponse containing merit list
	 */
	@GetMapping("meritlist")
	public ORSResponse getMeritList() {
		List<MarksheetDTO> list = baseService.getMeritList(userContext);
		ORSResponse res = new ORSResponse(true);
		res.addResult("list", list);
		return res;
	}
}