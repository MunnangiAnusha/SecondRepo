package com.invoiceservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.invoiceservice.entity.Invoice;


@Service
public interface InvoiceService {
	
public Invoice saveInvoice(Invoice invoice);
	
	//public Invoice getInvoiceById(Integer id);
	
	public List<Invoice> getAllInvoices();
	
	


}
