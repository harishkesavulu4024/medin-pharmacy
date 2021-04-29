package com.medin.pharmacy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.dto.CustomerOrderLineItemDTO;
import com.medin.pharmacy.dto.LoyaltyResponseDTO;
import com.medin.pharmacy.repository.LoyaltyLedgerRepository;
import com.medin.pharmacy.repository.LoyaltyTransactionRepository;
import com.medin.pharmacy.service.ICustomerLoyaltyService;
import com.medin.pharmacy.service.ILoyaltyTransactionService;
import com.medin.pharmacy.service.impl.mapper.LoyaltyTransactionMapper;

@Service
public class LoyaltyTransactionServiceImpl implements ILoyaltyTransactionService {

	@Autowired
	private LoyaltyTransactionRepository loyaltyTransactionRepository;

	@Autowired
	private LoyaltyLedgerRepository loyaltyLedgerRepository;

	@Autowired
	private LoyaltyTransactionMapper loyaltyTransanctionMapper;

	@Autowired
	private ICustomerLoyaltyService customerLoyaltyService;

	@Value("${transaction.burn.limit:200}")
	private double transactionBurnLimit;

	@Value("${transaction.earn.limit:200}")
	private double transactionEarnLimit;

	@Transactional(readOnly = true)
	@Override
	public LoyaltyResponseDTO earnableBurnablePoints(CustomerOrderDTO customerOrderDTO) {
		LoyaltyResponseDTO loyaltyResponse = null;
		Long customerId = customerOrderDTO.getCustomerId();
		CustomerLoyaltyDTO customerLoyaltyDTO = customerLoyaltyService
				.getCustomerLoyaltyDetailsByCustomerId(customerId);
		if (customerLoyaltyDTO != null) {
			Long totalLoyaltyPoints = customerLoyaltyDTO.getPoints();
			double burnablePoints = this.calculateBurnPoints(customerOrderDTO);
			if (burnablePoints > totalLoyaltyPoints) {
				burnablePoints = totalLoyaltyPoints;
			}
			double earnablePoints = this.calculateEarnPoints(customerOrderDTO, burnablePoints);
			loyaltyResponse = LoyaltyResponseDTO.builder().earnedPoints(earnablePoints).burnedPoints(burnablePoints)
					.overallPoints(totalLoyaltyPoints).customerId(customerId)
					.loyaltyReferenceId(customerOrderDTO.getOrderRefernceId()).build();
		}
		return loyaltyResponse;
	}

	private double calculateBurnPoints(CustomerOrderDTO customerOrderDTO) {
		double burnablePoints = 0l;
		List<CustomerOrderLineItemDTO> orderLineItems = customerOrderDTO.getOrderLineItems();
		if (orderLineItems != null && !orderLineItems.isEmpty()) {
			// validating all line items
			for (CustomerOrderLineItemDTO orderLineItem : orderLineItems) {
				Long quantity = orderLineItem.getQuantity();
				Double productPrice = orderLineItem.getProductPrice();
				Double totalLineItemPrices = quantity * productPrice;
				burnablePoints = burnablePoints + (totalLineItemPrices * 10 / 100);
			}
			// rounding to nearest number
			burnablePoints = Math.round(Math.min(burnablePoints, transactionBurnLimit));
		}
		return burnablePoints;
	}

	private double calculateEarnPoints(CustomerOrderDTO customerOrderDTO, double burnPoints) {
		double earnablePoints = 0l;
		List<CustomerOrderLineItemDTO> orderLineItems = customerOrderDTO.getOrderLineItems();
		if (orderLineItems != null && !orderLineItems.isEmpty()) {
			// validating all line items
			for (CustomerOrderLineItemDTO orderLineItem : orderLineItems) {
				Long quantity = orderLineItem.getQuantity();
				Double productPrice = orderLineItem.getProductPrice();
				Double totalLineItemPrices = quantity * productPrice;
				double burnableAmount = Math.min(totalLineItemPrices * 10 / 100, burnPoints);
				totalLineItemPrices = totalLineItemPrices - burnableAmount;
				if (totalLineItemPrices > 0) {
					earnablePoints = earnablePoints + (totalLineItemPrices * 10 / 100);
				}
				burnPoints = burnPoints - burnableAmount;
			}
			// rounding to nearest number
			earnablePoints = Math.round(Math.min(earnablePoints, transactionEarnLimit));
		}
		return earnablePoints;
	}

}
