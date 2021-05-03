package com.medin.pharmacy.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.dto.CustomerOrderLineItemDTO;
import com.medin.pharmacy.dto.LoyaltyLedgerDTO;
import com.medin.pharmacy.dto.LoyaltyResponseDTO;
import com.medin.pharmacy.dto.LoyaltyTransactionDTO;
import com.medin.pharmacy.entities.LoyaltyLedger;
import com.medin.pharmacy.entities.LoyaltyTransaction;
import com.medin.pharmacy.enums.LedgerType;
import com.medin.pharmacy.enums.LoyaltyStatus;
import com.medin.pharmacy.repository.CustomerLoyaltyRepository;
import com.medin.pharmacy.repository.LoyaltyLedgerRepository;
import com.medin.pharmacy.repository.LoyaltyTransactionRepository;
import com.medin.pharmacy.service.ICustomerLoyaltyService;
import com.medin.pharmacy.service.ILoyaltyTransactionService;
import com.medin.pharmacy.service.impl.mapper.CustomerLoyaltyMapper;
import com.medin.pharmacy.service.impl.mapper.LoyaltyLedgerMapper;
import com.medin.pharmacy.service.impl.mapper.LoyaltyTransactionMapper;
import com.medin.pharmacy.utils.DateUtil;

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

	@Autowired
	private CustomerLoyaltyRepository customerLoyaltyRepository;

	@Autowired
	private CustomerLoyaltyMapper customerLoyaltyMapper;

	@Autowired
	private LoyaltyLedgerMapper loyaltyLedgerMapper;

	private ObjectMapper objectmapper = new ObjectMapper();

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

	private long calculateBurnPoints(CustomerOrderDTO customerOrderDTO) {
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
		return (long) burnablePoints;
	}

	private long calculateEarnPoints(CustomerOrderDTO customerOrderDTO, double burnPoints) {
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
		return (long) earnablePoints;
	}

	@Transactional
	@Override
	public LoyaltyResponseDTO processLoyaltyRedemption(CustomerOrderDTO customerOrderDTO) {
		LoyaltyResponseDTO loyaltyResponse = null;
		Long customerId = customerOrderDTO.getCustomerId();
		CustomerLoyaltyDTO customerLoyaltyDTO = customerLoyaltyService
				.getCustomerLoyaltyDetailsByCustomerId(customerId);
		if (customerLoyaltyDTO != null) {
			long burnablePoints = this.calculateBurnPoints(customerOrderDTO);
			// consider customer loyalty points if burnable points gt than total
			// loyalty points
			if (burnablePoints > customerLoyaltyDTO.getPoints()) {
				burnablePoints = customerLoyaltyDTO.getPoints();
			}
			customerLoyaltyDTO.setPoints((customerLoyaltyDTO.getPoints() - burnablePoints));

			customerLoyaltyRepository
					.save(customerLoyaltyMapper.customerLoyaltyDTOToCustomerLoyalty(customerLoyaltyDTO));

			String loyaltyReferenceId = customerOrderDTO.getOrderRefernceId();
			LoyaltyTransaction loyaltyTransaction = loyaltyTransactionRepository
					.findByLoyaltyReferenceId(loyaltyReferenceId);

			LoyaltyTransactionDTO loyaltyTransactionDTO = null;
			if (loyaltyTransaction != null) {
				loyaltyTransactionDTO = loyaltyTransanctionMapper
						.loyaltyTransactionToLoyaltyTransactionDTO(loyaltyTransaction);
			} else {
				loyaltyTransactionDTO = new LoyaltyTransactionDTO();
				loyaltyTransactionDTO.setCustomerLoyalty(customerLoyaltyDTO);
				loyaltyTransactionDTO.setLoyaltyReferenceId(customerOrderDTO.getOrderRefernceId());
				loyaltyTransactionDTO.setLoyaltyType("PAYMENT");
			}
			try {
				loyaltyTransactionDTO.setData(objectmapper.writeValueAsString(customerOrderDTO));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			loyaltyTransactionDTO.setPointsRedeemed(burnablePoints);
			loyaltyTransactionDTO.setLoyaltyStatus(LoyaltyStatus.INITIATED);

			LoyaltyTransaction dbLoyaltyTransaction = loyaltyTransactionRepository
					.save(loyaltyTransanctionMapper.loyaltyTransactionDTOToLoyaltyTransaction(loyaltyTransactionDTO));

			Map<String, Long> reducePointsMap = this.reduceIssuePoints(burnablePoints, customerId);

			LoyaltyLedgerDTO loyaltyLedgerDTO = new LoyaltyLedgerDTO();
			loyaltyLedgerDTO.setLoyaltyTransaction(
					loyaltyTransanctionMapper.loyaltyTransactionToLoyaltyTransactionDTO(dbLoyaltyTransaction));
			loyaltyLedgerDTO.setPoints(burnablePoints);
			loyaltyLedgerDTO.setPointsRemaining(0l);
			loyaltyLedgerDTO.setBalance(customerLoyaltyDTO.getPoints());
			loyaltyLedgerDTO.setType(LedgerType.REDEEM);
			loyaltyLedgerDTO.setCustomerId(customerId);
			try {
				loyaltyLedgerDTO.setDetails(objectmapper.writeValueAsString(reducePointsMap));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			loyaltyLedgerDTO.setRemarks(
					"Redeemed " + burnablePoints + "points for ref_id :" + customerOrderDTO.getOrderRefernceId());

			LoyaltyLedger dbLoyaltyLedger = loyaltyLedgerRepository
					.save(loyaltyLedgerMapper.loyaltyLedgerDTOToLoyaltyLedger(loyaltyLedgerDTO));

			loyaltyResponse = LoyaltyResponseDTO.builder().earnedPoints(0).burnedPoints(burnablePoints)
					.overallPoints(customerLoyaltyDTO.getPoints()).customerId(customerId)
					.loyaltyReferenceId(customerOrderDTO.getOrderRefernceId()).build();
		}
		return loyaltyResponse;

	}

	@Transactional
	@Override
	public LoyaltyResponseDTO processLoyaltyIssuance(CustomerOrderDTO customerOrderDTO) {
		LoyaltyResponseDTO loyaltyResponse = null;
		Long customerId = customerOrderDTO.getCustomerId();
		long pointsToRedeem = customerOrderDTO.getPointsToRedeem();
		CustomerLoyaltyDTO customerLoyaltyDTO = customerLoyaltyService
				.getCustomerLoyaltyDetailsByCustomerId(customerId);
		if (customerLoyaltyDTO != null) {
			long earnablePoints = this.calculateEarnPoints(customerOrderDTO, pointsToRedeem);

			customerLoyaltyDTO.setPoints((customerLoyaltyDTO.getPoints() + earnablePoints));

			customerLoyaltyRepository
					.save(customerLoyaltyMapper.customerLoyaltyDTOToCustomerLoyalty(customerLoyaltyDTO));

			// create loyalty Transaction and loyalty ledger

			LoyaltyTransaction loyaltyTransaction = loyaltyTransactionRepository
					.findByLoyaltyReferenceId(customerOrderDTO.getOrderRefernceId());
			LoyaltyTransactionDTO loyaltyTransactionDTO = null;
			if (loyaltyTransaction != null) {
				loyaltyTransactionDTO = loyaltyTransanctionMapper
						.loyaltyTransactionToLoyaltyTransactionDTO(loyaltyTransaction);
			} else {
				loyaltyTransactionDTO = new LoyaltyTransactionDTO();
				loyaltyTransactionDTO.setCustomerLoyalty(customerLoyaltyDTO);
				loyaltyTransactionDTO.setLoyaltyReferenceId(customerOrderDTO.getOrderRefernceId());
				loyaltyTransactionDTO.setLoyaltyType("PAYMENT");
			}
			try {
				loyaltyTransactionDTO.setData(objectmapper.writeValueAsString(customerOrderDTO));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			loyaltyTransactionDTO.setPointsEarned(earnablePoints);
			loyaltyTransactionDTO.setLoyaltyStatus(LoyaltyStatus.COMPLETED);

			LoyaltyTransaction dbLoyaltyTransaction = loyaltyTransactionRepository
					.save(loyaltyTransanctionMapper.loyaltyTransactionDTOToLoyaltyTransaction(loyaltyTransactionDTO));

			LoyaltyLedgerDTO loyaltyLedgerDTO = new LoyaltyLedgerDTO();
			loyaltyLedgerDTO.setLoyaltyTransaction(
					loyaltyTransanctionMapper.loyaltyTransactionToLoyaltyTransactionDTO(dbLoyaltyTransaction));
			loyaltyLedgerDTO.setPoints(earnablePoints);
			loyaltyLedgerDTO.setPointsRemaining(earnablePoints);
			loyaltyLedgerDTO.setBalance(customerLoyaltyDTO.getPoints());
			loyaltyLedgerDTO.setExpiryDate(DateUtil.addMonthsToUserDate(new Date(), 3));
			loyaltyLedgerDTO.setType(LedgerType.ISSUE);
			loyaltyLedgerDTO.setCustomerId(customerId);
			loyaltyLedgerDTO.setRemarks(
					"Earned " + earnablePoints + "points for ref_id :" + customerOrderDTO.getOrderRefernceId());

			LoyaltyLedger dbLoyaltyLedger = loyaltyLedgerRepository
					.save(loyaltyLedgerMapper.loyaltyLedgerDTOToLoyaltyLedger(loyaltyLedgerDTO));

			loyaltyResponse = LoyaltyResponseDTO.builder().earnedPoints(earnablePoints).burnedPoints(0)
					.overallPoints(customerLoyaltyDTO.getPoints()).customerId(customerId)
					.loyaltyReferenceId(customerOrderDTO.getOrderRefernceId()).build();
		}
		return loyaltyResponse;
	}

	@Transactional
	private Map<String, Long> reduceIssuePoints(long burnedPoints, long customerId) {
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();
		long toBeBurnPoints = burnedPoints;
		List<LoyaltyLedger> loyaltyLedgerList = loyaltyLedgerRepository
				.findEarnLedgerTransactionByCustomerId(customerId);
		if (loyaltyLedgerList != null && !loyaltyLedgerList.isEmpty()) {
			for (LoyaltyLedger loyaltyLedger : loyaltyLedgerList) {
				long pointsRemaining = loyaltyLedger.getPointsRemaining();
				if (pointsRemaining > toBeBurnPoints) {
					loyaltyLedger.setPointsRemaining(pointsRemaining - toBeBurnPoints);
					resultMap.put(String.valueOf(loyaltyLedger.getId()), toBeBurnPoints);
					toBeBurnPoints = 0;
				} else {
					toBeBurnPoints = toBeBurnPoints - pointsRemaining;
					resultMap.put(String.valueOf(loyaltyLedger.getId()), pointsRemaining);
					loyaltyLedger.setPointsRemaining(0l);
				}

				loyaltyLedgerRepository.save(loyaltyLedger);
			}
		}
		return resultMap;
	}

}
