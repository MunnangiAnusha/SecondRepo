package com.invoiceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoiceservice.entity.Invoice;
@Repository
public interface InvoiceServiceDao extends JpaRepository<Invoice, Integer>{

}
