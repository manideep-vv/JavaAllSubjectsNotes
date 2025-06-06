package com.bharath.ws.soap.dto;


import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Date;

@XmlType(propOrder = {"creditCardInfo","amount"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "paymentProcessorReq")
public class PaymentProcessorRequest {

	@XmlElement(name = "creditCardInfo")
	private CreditCardInfo creditCardInfo;
	@XmlElement(name = "amount")
	private Double amount;

	public PaymentProcessorRequest(CreditCardInfo creditCardInfo, Double amount) {
		this.creditCardInfo = creditCardInfo;
		this.amount = amount;
	}

	public PaymentProcessorRequest() {
	}

	public CreditCardInfo getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public static void main(String[] args) throws JAXBException {
		System.out.println("marshalling starts");

		CreditCardInfo creditCardInfo1=new CreditCardInfo("122112",new Date(),"Mani","voleti","C","Kavali");
		PaymentProcessorRequest paymentProcessorRequest=new PaymentProcessorRequest(creditCardInfo1,120.2);
		PaymentProcessorResponse paymentProcessorResponse =new PaymentProcessorResponse();
		paymentProcessorResponse.setResult(true);
//		JAXBContext jaxbContext = JAXBContext.newInstance(PaymentProcessorRequest.class);
//		Marshaller marshaller = jaxbContext.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
//		StringWriter writer = new StringWriter();
//		marshaller.marshal(paymentProcessorRequest,new StreamResult(writer));
//		System.out.println(writer.toString());
//		System.out.println("marshalled");
		JAXBContext jaxbContext = JAXBContext.newInstance(PaymentProcessorResponse.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(paymentProcessorResponse,new StreamResult(writer));
		System.out.println(writer.toString());
		System.out.println("marshalled");

	}

}
