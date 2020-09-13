package com.invoiceservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceservice.controller.InvoiceServiceController;
import com.invoiceservice.dao.InvoiceServiceDao;
import com.invoiceservice.entity.Invoice;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	/*
	 * @Autowired private InvoiceServiceCache invoiceServiceCache;
	 */
	private static final Logger  LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class.getPackage().getName());
	
	@Autowired
	private InvoiceServiceDao invoiceServiceDao;
	static String invoiceNumber = "INV-001";
	
	/*
	 * save invoice
	 */
	@Override
	public Invoice saveInvoice(Invoice invoice) {
		Invoice inv = null;
		invoiceNumber = "INV-00" + (Integer.parseInt(invoiceNumber.substring(6, invoiceNumber.length())) + 1);
		LOGGER.trace("saveInvoice in service layer");
		try {
			
		//	inv = invoiceServiceCache.saveInvoice(invoice);
			invoice.setInvoiceNumber(invoiceNumber);
			inv =invoiceServiceDao.save(invoice);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return inv;

	}
	/*
	 * Fetch all invoices
	 */

	@Override
	public List<Invoice> getAllInvoices() {
		LOGGER.trace("All invoices in service layer");
		List<Invoice> list = null;
		try {
			list =invoiceServiceDao.findAll();
		//	list = invoiceServiceCache.getAllInvoices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
