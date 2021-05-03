package com.medin.pharmacy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.ConfirmOrderDTO;
import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.dto.CustomerOrderLineItemDTO;
import com.medin.pharmacy.dto.LoyaltyResponseDTO;
import com.medin.pharmacy.dto.ProductDTO;
import com.medin.pharmacy.entities.CustomerOrder;
import com.medin.pharmacy.entities.CustomerOrderLineItem;
import com.medin.pharmacy.enums.OrderStatus;
import com.medin.pharmacy.repository.CustomerOrderItemRepository;
import com.medin.pharmacy.repository.CustomerOrderRepository;
import com.medin.pharmacy.service.ICustomerOrderService;
import com.medin.pharmacy.service.ICustomerService;
import com.medin.pharmacy.service.ILoyaltyTransactionService;
import com.medin.pharmacy.service.IProductService;
import com.medin.pharmacy.service.impl.mapper.CustomerOrderMapper;
import com.medin.pharmacy.utils.BusinessException;
import com.medin.pharmacy.utils.StringUtils;

@Service
public class CustomerOrderServiceImpl implements ICustomerOrderService {

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Autowired
	private CustomerOrderItemRepository customerOrderLineItemRepository;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IProductService productService;

	@Autowired
	private CustomerOrderMapper customerOrderMapper;

	@Autowired
	private Environment env;

	@Autowired
	private ILoyaltyTransactionService loyaltyTransactionService;

	@Value("${loyalty.enabled:true}")
	private boolean isLoyaltyEnabled;

	@Override
	@Transactional
	public CustomerOrderDTO createOrder(CustomerOrderDTO customerOrderDTO) {
		Long storeId = customerOrderDTO.getStoreId();
		Long customerId = customerOrderDTO.getCustomerId();
		CustomerDTO customerDTO = customerService.getCustomerById(customerId);
		if (customerDTO == null) {
			throw new BusinessException("Customer not found with id:- " + customerId);
		}
		String referenceId = StringUtils.getRandomDigits(9);
		customerOrderDTO.setOrderRefernceId(referenceId);
		customerOrderDTO.setOrderStatus(OrderStatus.CART_DRAFT);
		// save order line items
		customerOrderDTO = this.saveOrderLineItems(customerOrderDTO);
		// save customer order
		CustomerOrder dbCustomerOrder = customerOrderRepository
				.save(customerOrderMapper.customerOrderDTOToCustomerOrder(customerOrderDTO));
		CustomerOrderDTO dbCustomerOrderDTO = customerOrderMapper.customerOrderToCustomerOrderDTO(dbCustomerOrder);
		// check if loyalty enabled
		if (isLoyaltyEnabled) {
			LoyaltyResponseDTO loyaltyResponseDTO = loyaltyTransactionService
					.earnableBurnablePoints(dbCustomerOrderDTO);
			dbCustomerOrderDTO.setLoyaltyData(loyaltyResponseDTO);
		}
		return dbCustomerOrderDTO;
	}

	@Override
	@Transactional
	public CustomerOrderDTO updateOrder(CustomerOrderDTO customerOrderDTO) {
		CustomerOrderDTO dbCustomerOrderDTO = null;
		Long customerOrderId = customerOrderDTO.getId();
		Long customerId = customerOrderDTO.getCustomerId();
		CustomerOrder dbCustomerOrder = customerOrderRepository.findByIdAndCustomerId(customerOrderId, customerId);
		if (dbCustomerOrder != null) {
			// throws error if created-time > 1 hour
			boolean isOrderExpired = this.checkOrderExpiry(dbCustomerOrder);
			if (isOrderExpired) {
				throw new BusinessException("Please create a new cart to checkout");
			}
			if (!customerOrderDTO.getOrderRefernceId().equalsIgnoreCase(dbCustomerOrder.getOrderRefernceId())) {
				throw new BusinessException(
						"Update Order APP Reference ID is not matched with created APP ReferenceID");
			}
			// Delete all existing order items
			customerOrderLineItemRepository.deleteByCustomerOrderId(customerOrderId);
			// Add new order line items
			customerOrderDTO = this.saveOrderLineItems(customerOrderDTO);
			customerOrderDTO.setOrderStatus(OrderStatus.UPDATE_CART_DRAFT);
			CustomerOrder customerOrder = customerOrderRepository
					.save(customerOrderMapper.customerOrderDTOToCustomerOrder(customerOrderDTO));
			dbCustomerOrderDTO = customerOrderMapper.customerOrderToCustomerOrderDTO(customerOrder);
			// check if loyalty enabled
			if (isLoyaltyEnabled) {
				LoyaltyResponseDTO loyaltyResponseDTO = loyaltyTransactionService
						.earnableBurnablePoints(dbCustomerOrderDTO);
				dbCustomerOrderDTO.setLoyaltyData(loyaltyResponseDTO);
			}
		} else {
			throw new BusinessException(" Customer order not found with id: " + customerOrderId);
		}
		return dbCustomerOrderDTO;
	}

	private CustomerOrderDTO saveOrderLineItems(CustomerOrderDTO customerOrderDTO) {
		Double totalProductPrice = 0d;
		Double totalTaxPrice = 0d;
		Double totalProductCharges = 0d;
		Double totalAmount = 0d;
		List<CustomerOrderLineItemDTO> orderLineItems = customerOrderDTO.getOrderLineItems();
		if (orderLineItems != null && !orderLineItems.isEmpty()) {
			// validating all line items
			for (CustomerOrderLineItemDTO orderLineItem : orderLineItems) {
				Long quantity = orderLineItem.getQuantity();
				String productId = orderLineItem.getProductId();
				ProductDTO productDTO = productService.getProductById(productId);
				if (productDTO != null) {
					Double sellingPrice = productDTO.getSellingPrice();
					Double mrpPrice = productDTO.getMrpPrice();
					Double totalLineItemPrices = quantity * sellingPrice;
					orderLineItem.setTotalLineItemPrices(totalLineItemPrices);
					orderLineItem.setProductPrice(sellingPrice);
					// calculate line Item charges and tax
					Double totalLineItemCharges = 0d;
					Double totalLineItemTax = 0d;
					Double totalLineItemAmount = totalLineItemCharges + totalLineItemTax + totalLineItemPrices;
					orderLineItem.setTotalLineItemAmount(totalLineItemAmount);

					totalProductPrice += totalLineItemPrices;
					totalProductCharges += totalLineItemCharges;
					totalTaxPrice += totalLineItemTax;
					totalAmount += totalLineItemAmount;

				} else {
					throw new BusinessException("Product not found for id:-" + productId);
				}
			}
		} else {
			throw new BusinessException("Order Line items cannot be empty");
		}
		customerOrderDTO.setTotalAmount(totalAmount);
		customerOrderDTO.setTotalProductCharges(totalProductCharges);
		customerOrderDTO.setTotalProductPrice(totalProductPrice);
		customerOrderDTO.setTotalTaxPrice(totalTaxPrice);
		return customerOrderDTO;
	}

	@Override
	public Boolean checkOrderExpiry(CustomerOrder customerOrder) {
		Date orderCreatedDate = customerOrder.getCreatedTime();
		Date currDate = new Date();

		String diff[] = new String[4];
		long timeDiff = Math.abs(orderCreatedDate.getTime() - currDate.getTime());
		diff = String.format("%d Hours %d Minutes", TimeUnit.MILLISECONDS.toHours(timeDiff),
				TimeUnit.MILLISECONDS.toMinutes(timeDiff)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)))
				.split(" ");
		int diffInHours = Integer.valueOf(diff[0]);

		int diffInMinutes = Integer.valueOf(diff[2]);
		int configuredOrderExpiredTimeHours = Integer
				.valueOf(env.getProperty("order.expire.time.format.hours", Integer.class, 1));
		int configuredOrderExpiredTimeMinutes = Integer
				.valueOf(env.getProperty("order.expire.time.format.minutes", Integer.class, 0));
		Boolean isHoursExpired = Boolean.FALSE;
		Boolean isMinutesExpired = Boolean.FALSE;
		Boolean isOrderExpired = Boolean.FALSE;

		// throw error if the status is already OrderExpired
		if (customerOrder.getOrderStatus().equals(OrderStatus.ORDER_EXPIRED.toString())) {
			throw new BusinessException("Please create a new cart to place an order");
		}

		if (diffInHours >= configuredOrderExpiredTimeHours)
			isHoursExpired = Boolean.TRUE;

		if (diffInMinutes >= configuredOrderExpiredTimeMinutes)
			isMinutesExpired = Boolean.TRUE;
		if (isHoursExpired && isMinutesExpired)
			isOrderExpired = Boolean.TRUE;

		return isOrderExpired;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerOrderDTO> getCustomerOrdersByMobileNumber(String mobileNumber) {
		List<CustomerOrderDTO> customerOrderList = null;
		CustomerDTO customerDTO = customerService.getCustomerDetailsByPhoneNumber(mobileNumber);
		if (customerDTO != null) {
			customerOrderList = this.getCustomerOrdersByCustomerId(customerDTO.getId());
		} else {
			throw new BusinessException("Customer not found with mobileNumber:- " + mobileNumber);
		}
		return customerOrderList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerOrderDTO> getCustomerOrdersByCustomerId(Long customerId) {
		List<CustomerOrderDTO> customerOrderDTOs = null;
		List<CustomerOrder> customerOrderList = customerOrderRepository.findByCustomerId(customerId);
		if (customerOrderList != null && !customerOrderList.isEmpty()) {
			customerOrderDTOs = customerOrderMapper.listOfCustomerOrderToCustomerOrderDTO(customerOrderList);
		}
		return customerOrderDTOs;
	}

	@Override
	@Transactional
	public CustomerOrderDTO confirmOrder(ConfirmOrderDTO confirmOrderDTO) {
		CustomerOrderDTO dbCustomerOrderDTO = null;
		Long customerOrderId = confirmOrderDTO.getOrderId();
		Long customerId = confirmOrderDTO.getCustomerId();
		long pointsToRedeem = confirmOrderDTO.getPointsToRedeem();
		long pointsToEarn = confirmOrderDTO.getPointsToEarn();
		CustomerOrder dbCustomerOrder = customerOrderRepository.findByIdAndCustomerId(customerOrderId, customerId);
		if (dbCustomerOrder != null) {
			String orderStatus = dbCustomerOrder.getOrderStatus().toString();
			if (orderStatus != null && (orderStatus.equals(OrderStatus.CART_DRAFT.toString())
					|| orderStatus.equals(OrderStatus.UPDATE_CART_DRAFT.toString()))) {
				List<CustomerOrderLineItem> customerOrderLineItems = dbCustomerOrder.getOrderLineItems();
				if (customerOrderLineItems != null && !customerOrderLineItems.isEmpty()) {
					for (CustomerOrderLineItem customerOrderLineItem : customerOrderLineItems) {
						Long quantity = customerOrderLineItem.getQuantity();
						String productId = customerOrderLineItem.getProductId();
						Double productPrice = customerOrderLineItem.getProductPrice();
						ProductDTO productDTO = productService.getProductById(productId);
						if (productDTO != null) {
							long totalProductQuantity = productDTO.getTotalQuantity();
							long diffProdQuantity = totalProductQuantity - quantity;
							productDTO.setTotalQuantity((int) diffProdQuantity);
							// Updating product quantity every time order
							// confirms
							productService.updateProduct(productDTO);
						}
					}
					dbCustomerOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
					// update order created status
					CustomerOrder updateCustomerOrder = customerOrderRepository.save(dbCustomerOrder);
					dbCustomerOrderDTO = customerOrderMapper.customerOrderToCustomerOrderDTO(updateCustomerOrder);
					// Earn and burn happen only if loyaltty enabled
					if (isLoyaltyEnabled) {
						// Redeem points from order
						if (pointsToRedeem > 0) {
							LoyaltyResponseDTO loyaltyRedempResponseDTO = loyaltyTransactionService
									.processLoyaltyRedemption(dbCustomerOrderDTO);
							dbCustomerOrderDTO.setPointsToRedeem((long) loyaltyRedempResponseDTO.getBurnedPoints());
						}
						// Earn points of an order
						if (pointsToEarn > 0) {
							LoyaltyResponseDTO loyaltyRedempResponseDTO = loyaltyTransactionService
									.processLoyaltyIssuance(dbCustomerOrderDTO);
						}
					}
				}
			} else {
				throw new BusinessException("Customer order already confirmed,please create new order");
			}
		} else {
			throw new BusinessException("Customer order not found to confirm");
		}
		return dbCustomerOrderDTO;
	}

}
