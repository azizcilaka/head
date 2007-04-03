package org.mifos.migration.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mifos.application.accounts.business.AccountFeesEntity;
import org.mifos.application.customer.business.CustomFieldDefinitionEntity;
import org.mifos.application.customer.business.CustomFieldView;
import org.mifos.application.customer.business.CustomerCustomFieldEntity;
import org.mifos.application.customer.center.business.CenterBO;
import org.mifos.application.customer.exceptions.CustomerException;
import org.mifos.application.fees.business.FeeBO;
import org.mifos.application.fees.business.FeeView;
import org.mifos.application.fees.persistence.FeePersistence;
import org.mifos.application.meeting.business.MeetingBO;
import org.mifos.application.util.helpers.CustomFieldType;
import org.mifos.framework.security.util.UserContext;
import org.mifos.migration.generated.Center;
import org.mifos.migration.generated.FeeAmount;


public class CenterMapper {
	public static final Short MEETING_TIME_CUSTOM_FIELD_ID = (short) 5;
	public static final Short DISTANCE_FROM_BRANCH_OFFICE_CUSTOM_FIELD_ID = (short) 6;

	public static CenterBO mapCenterToCenterBO(Center center,
			UserContext userContext) {
		org.mifos.framework.business.util.Address address = 
			AddressMapper.mapXMLAddressToMifosAddress(center.getAddress());
		MeetingBO meeting = null;
		Date mfiJoiningDate = mapXMLGregorianCalendarToDate(
			center.getMfiJoiningDate());
		if (center.getMonthlyMeeting() != null) {
			meeting = MeetingMapper.mapMonthlyMeetingToMeetingBO(
				center.getMonthlyMeeting());
		}
		else {
			meeting = MeetingMapper.mapWeeklyMeetingToMeetingBO(
				center.getWeeklyMeeting());
		}

		FeePersistence feePersistence = new FeePersistence();
		List<FeeView> fees = new ArrayList<FeeView>();
		for (FeeAmount feeAmount : center.getFeeAmount()) {
			FeeBO fee = feePersistence.getFee(feeAmount.getFeeId());
			FeeView feeView = new FeeView(userContext, fee);
			feeView.setAmount(feeAmount.getAmount().toPlainString());
			if (fee == null) {
				// TODO: report invalid fee reference
			}
			else {
				fees.add(feeView);
			}
		}

		// TODO: generalize this
		List<CustomFieldView> fields = new ArrayList<CustomFieldView>();
		fields.add(new CustomFieldView(
			MEETING_TIME_CUSTOM_FIELD_ID, 
			center.getMeetingTime(), 
			CustomFieldType.ALPHA_NUMERIC.getValue()));
		fields.add(new CustomFieldView(
			DISTANCE_FROM_BRANCH_OFFICE_CUSTOM_FIELD_ID, 
			""+ center.getDistanceFromBranchOffice(),
			CustomFieldType.NUMERIC.getValue()));

		CenterBO centerBO;
		try {
			centerBO = new CenterBO(userContext, center.getName(), address,
					fields, fees, center.getExternalId(), mfiJoiningDate,
					center.getOfficeId(), meeting, center.getLoanOfficerId());
		}
		catch (CustomerException e) {
			throw new RuntimeException(e);
		}


		return centerBO;
	}

	public static Date mapXMLGregorianCalendarToDate(
			XMLGregorianCalendar calendar) {
		if (calendar == null)
			return null;

		return calendar.toGregorianCalendar().getTime();
	}

	public static XMLGregorianCalendar mapDateToXMLGregorianCalendar(Date date) {
		if (date == null)
			return null;

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		DatatypeFactory datatypeFactory;
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		}
		catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		XMLGregorianCalendar xmlCalendar = datatypeFactory
				.newXMLGregorianCalendar(calendar);
		// we want only the date, so don't use a timezone
		xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

		return xmlCalendar;
	}

	public static Center mapCenterBOToCenter(CenterBO center) {
		Center newCenter = new Center();
		newCenter.setName(center.getDisplayName());
		newCenter.setOfficeId(center.getOffice().getOfficeId());
		newCenter.setLoanOfficerId(center.getPersonnel().getPersonnelId());
		MeetingBO meeting = center.getCustomerMeeting().getMeeting();
		if (meeting.isMonthly()) {
			newCenter.setMonthlyMeeting(
				MeetingMapper.mapMeetingBOToMonthlyMeeting(meeting));
		}
		else if (meeting.isWeekly()) {
			newCenter.setWeeklyMeeting(
				MeetingMapper.mapMeetingBOToWeeklyMeeting(meeting));
		}
		else {
			throw new RuntimeException(
					"Unhandled meeting type (not MONTHLY or WEEKLY)");
		}
		newCenter.setExternalId(center.getExternalId());
		newCenter.setMfiJoiningDate(mapDateToXMLGregorianCalendar(
			center.getMfiJoiningDate()));
		newCenter.setAddress(AddressMapper.mapMifosAddressToXMLAddress(
			center.getAddress()));

		// TODO: generalize this
		Set<CustomerCustomFieldEntity> fields = center.getCustomFields();
		for (CustomerCustomFieldEntity field : fields) {
			if (field.getFieldId().shortValue() == 
				MEETING_TIME_CUSTOM_FIELD_ID.shortValue()) {
					newCenter.setMeetingTime(field.getFieldValue());
			} else if(field.getFieldId().shortValue() == 
				DISTANCE_FROM_BRANCH_OFFICE_CUSTOM_FIELD_ID.shortValue()) {
					newCenter.setDistanceFromBranchOffice(
						Integer.valueOf(field.getFieldValue()));
			} else {
				throw new RuntimeException("Unexpected field id: " + field.getFieldId());
			}
		}

		List<FeeAmount> feeAmounts = newCenter.getFeeAmount();
		Set<AccountFeesEntity> accountFees = 
			center.getCustomerAccount().getAccountFees();
		for (AccountFeesEntity feeEntity : accountFees) {
			FeeBO fee = feeEntity.getFees();
			Double amount = feeEntity.getFeeAmount();
			FeeAmount feeAmount = new FeeAmount();
			feeAmount.setAmount(BigDecimal.valueOf(amount));
			feeAmount.setFeeId(fee.getFeeId());
			feeAmounts.add(feeAmount);
		}

		return newCenter;
	}
}
