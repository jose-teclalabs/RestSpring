package com.journaldev.spring.jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journaldev.spring.jdbc.model.Empleados;
import com.journaldev.spring.jdbc.model.Employee;

@Controller
public class EmpleadosController {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadosController.class);

	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@RequestMapping(value = "/rest/empleados", method = RequestMethod.GET)
	public @ResponseBody List<Empleados> getEmpleados() {
		logger.info("Start getEmpleados.");
		List<Empleados> empList = new ArrayList<Empleados>();
		//JDBC Code - Start
		String query = "select idUsuario, usuario, clave from Empleados";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> empRow : empRows){
			Empleados emp = new Empleados();
			emp.setIdUsuario(Integer.parseInt(String.valueOf(empRow.get("idUsuario"))));
			emp.setUsuario(String.valueOf(empRow.get("usuario")));
			emp.setClave(String.valueOf(empRow.get("clave")));
			empList.add(emp);
		}
		
		return empList;
	}
}
