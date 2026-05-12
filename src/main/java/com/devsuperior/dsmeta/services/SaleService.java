package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {

		LocalDate min;
		LocalDate max;
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (maxDate == null) {
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}

		if (minDate == null) {
			min = max.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}
		
		if (name == null) {
			name = "";
		}

		return repository.search(min, max, name, pageable);
	}

	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {
		LocalDate min;
		LocalDate max;
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (maxDate == null) {
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}

		if (minDate == null) {
			min = max.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}

		return repository.search2(min, max);
	}
}
