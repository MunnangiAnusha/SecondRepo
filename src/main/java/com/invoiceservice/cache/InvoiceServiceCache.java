package com.invoiceservice.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.invoiceservice.entity.Invoice;
/*
 * Cache Service for Strong data instead of database
 */
@Component
public class InvoiceServiceCache {

	static String invoiceNumber = "INV-001";
	public static Map<String, Invoice> invoicemap = new HashMap<>();

	public Invoice saveInvoice(Invoice invoice) {

		invoice.setInvoiceNumber(invoiceNumber);

		invoicemap.put(invoiceNumber, invoice);
	
		invoiceNumber = "INV-00" + (Integer.parseInt(invoiceNumber.substring(6, invoiceNumber.length())) + 1);

		return invoice;

	}
	

	public List<Invoice> getAllInvoices() {
		return new ArrayList<>(invoicemap.values());

	}
}
