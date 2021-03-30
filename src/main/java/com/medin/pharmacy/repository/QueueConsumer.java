package com.medin.pharmacy.repository;

public interface QueueConsumer {

	void receiveOtpMessage(String otpSms);

}