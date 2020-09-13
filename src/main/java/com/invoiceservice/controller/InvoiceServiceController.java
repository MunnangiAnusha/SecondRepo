package com.invoiceservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceservice.entity.Invoice;
import com.invoiceservice.service.InvoiceService;

@RestController
public class InvoiceServiceController {
	
	private static final Logger  LOGGER = LoggerFactory.getLogger(InvoiceServiceController.class.getPackage().getName());
	
	@Autowired
	private InvoiceService invoiceService;

	@PostMapping(value = "/invoice")
	public ResponseEntity<Object> saveInvoice(@RequestBody Invoice invoice) {
		LOGGER.trace("in saveInvoice");
		Invoice inv = invoiceService.saveInvoice(invoice);
		if (inv == null) {
			return new ResponseEntity<>("Failed to save invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(inv, HttpStatus.CREATED);

	}

	@GetMapping(value = "/invoices")
	public ResponseEntity<Object> getAllInvoices() {
		LOGGER.trace("in getAllInvoices");
		List<Invoice> list = invoiceService.getAllInvoices();
		if (list == null || list.size() == 0) {
			return new ResponseEntity<>("No records found", HttpStatus.OK);
		}
		return new ResponseEntity<>(list, HttpStatus.CREATED);

	}

}
