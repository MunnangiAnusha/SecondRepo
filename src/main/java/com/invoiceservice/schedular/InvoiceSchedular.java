package com.invoiceservice.schedular;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.invoiceservice.entity.Invoice;
import com.invoiceservice.entity.PaymentTerm;
import com.invoiceservice.service.InvoiceService;

@Component
public class InvoiceSchedular {

	private static final Logger  LOGGER = LoggerFactory.getLogger(InvoiceSchedular.class.getPackage().getName());
	
	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	RestTemplate restTemplate;

	/*
	 * This method calls every day mid night 12am
	 * 
	 */

	// @Scheduled(cron = "0/20 * * * * ?")
	@Scheduled(cron = "0 0 * * * ?")
	public void invoiceSchedular() {
		LOGGER.trace("into  the Schedular ");
		Date d = new Date();
		int shedularDay = d.getDate();
		int shedularMonth = d.getMonth() + 1;
		try {
			ResponseEntity<PaymentTerm[]> response = restTemplate.getForEntity("http://localhost:8585/payment-terms",
					PaymentTerm[].class);
			List<PaymentTerm> paymentTerms = Arrays.asList(response.getBody());
			LOGGER.info("payment-terms size " + paymentTerms.size());
			List<Invoice> invoiceList = invoiceService.getAllInvoices();
			List<Invoice> unpaidinvoices = invoiceList.stream().filter(i -> i.getStatus().equalsIgnoreCase("UNPAID"))
					.collect(Collectors.toList());
			LOGGER.info("unpaid invoices size " + unpaidinvoices.size());
			for (Invoice invoice : unpaidinvoices) {
				for (PaymentTerm paymentterm : paymentTerms) {
					if (invoice.getPaymentTerm().equals(paymentterm.getCode())) {
						String invoiceDate = invoice.getInvoiceDate();
						int daysdff = paymentterm.getDays();
						LOGGER.info("payment-term days " + daysdff);
						int remainderDays = paymentterm.getRemindBeforeDays();
						LOGGER.info("payment-term remainderDays " + remainderDays);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.setTime(sdf.parse(invoiceDate));
						System.out.println("time " + c.getTime());
						int dff = daysdff - remainderDays;
						c.add(c.DAY_OF_MONTH, dff);
						int invoiceDay = c.getTime().getDate();
						int invoiceMonth = c.getTime().getMonth() + 1;
						if (shedularDay == invoiceDay && shedularMonth == invoiceMonth) {
							System.out.println("Reminder sent for Invoice " + invoice.getInvoiceNumber());
						}

					}

				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
