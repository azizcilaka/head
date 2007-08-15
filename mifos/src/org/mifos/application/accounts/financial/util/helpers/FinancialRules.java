/**

 * FinancialRules.java    version: 1.0

 

 * Copyright (c) 2005-2006 Grameen Foundation USA

 * 1029 Vermont Avenue, NW, Suite 400, Washington DC 20005

 * All rights reserved.

 

 * Apache License 
 * Copyright (c) 2005-2006 Grameen Foundation USA 
 * 

 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the 

 * License. 
 * 
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an explanation of the license 

 * and how it is applied.  

 *

 */

package org.mifos.application.accounts.financial.util.helpers;

import org.mifos.application.accounts.financial.exceptions.FinancialException;
import org.mifos.application.accounts.financial.exceptions.FinancialExceptionConstants;

public class FinancialRules {
	public static short getCategoryAssociatedToAction(short financialActionId,
			Short type) throws FinancialException {
		FinancialActionConstants financialAction = FinancialActionConstants.getFinancialAction(financialActionId);
		return getCategoryAssociatedToAction(financialAction, type);
	}
	
	public static short getCategoryAssociatedToAction(FinancialActionConstants financialAction,
				Short type) throws FinancialException {
		if ((financialAction == FinancialActionConstants.PRINCIPALPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.PRINCIPALPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.LOANSADVANCES;

		if ((financialAction == FinancialActionConstants.INTERESTPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.INTERESTPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.INTERESTINCOMELOANS;

		if ((financialAction == FinancialActionConstants.FEEPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.FEEPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.INCOMEMICROCREDIT;

		if ((financialAction == FinancialActionConstants.PENALTYPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKBALANCES;
		if ((financialAction == FinancialActionConstants.PENALTYPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.PENALTY;

		if ((financialAction == FinancialActionConstants.ROUNDING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.ROUNDINGGL;
		if ((financialAction == FinancialActionConstants.ROUNDING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.ROUNDINGGL;
		if ((financialAction == FinancialActionConstants.MANDATORYDEPOSIT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;

		if ((financialAction == FinancialActionConstants.MANDATORYDEPOSIT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.MANDATORYSAVINGS;
		if ((financialAction == FinancialActionConstants.VOLUNTORYDEPOSIT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.VOLUNTORYDEPOSIT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.CLIENTSDEPOSITS;
		
		if ((financialAction == FinancialActionConstants.MANDATORYWITHDRAWAL)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.MANDATORYSAVINGS;
		if ((financialAction == FinancialActionConstants.MANDATORYWITHDRAWAL)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.VOLUNTORYWITHDRAWAL)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.CLIENTSDEPOSITS;
		if ((financialAction == FinancialActionConstants.VOLUNTORYWITHDRAWAL)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.BANKACCOUNTONE;
		
		if ((financialAction == FinancialActionConstants.SAVINGS_INTERESTPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.DIRECTEXPENDITURE;
		if ((financialAction == FinancialActionConstants.SAVINGS_INTERESTPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.SAVINGSMANDATORY;

		if ((financialAction == FinancialActionConstants.DISBURSAL)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.DISBURSAL)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.LOANTOCLIENTS;
		
		if ((financialAction == FinancialActionConstants.MISCFEEPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.MISCFEEPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.FEES;
		
		if ((financialAction == FinancialActionConstants.MISCPENALTYPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.MISCPENALTYPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.PENALTY;
		if ((financialAction == FinancialActionConstants.CUSTOMERACCOUNTMISCFEESPOSTING)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.CUSTOMERACCOUNTMISCFEESPOSTING)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.FEES;
		
		if ((financialAction == FinancialActionConstants.MANDATORYDEPOSIT_ADJUSTMENT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.MANDATORYSAVINGS;
		if ((financialAction == FinancialActionConstants.MANDATORYDEPOSIT_ADJUSTMENT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.BANKACCOUNTONE;
		
		if ((financialAction == FinancialActionConstants.VOLUNTORYDEPOSIT_ADJUSTMENT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.CLIENTSDEPOSITS;
		if ((financialAction == FinancialActionConstants.VOLUNTORYDEPOSIT_ADJUSTMENT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.BANKACCOUNTONE;
		
		if ((financialAction == FinancialActionConstants.MANDATORYWITHDRAWAL_ADJUSTMENT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.MANDATORYWITHDRAWAL_ADJUSTMENT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.MANDATORYSAVINGS;
		
		if ((financialAction == FinancialActionConstants.VOLUNTORYWITHDRAWAL_ADJUSTMENT)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.BANKACCOUNTONE;
		if ((financialAction == FinancialActionConstants.VOLUNTORYWITHDRAWAL_ADJUSTMENT)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.CLIENTSDEPOSITS;	
		
		if ((financialAction == FinancialActionConstants.WRITEOFF)
				&& (type.equals(FinancialConstants.DEBIT)))
			return CategoryConstants.WRITEOFFS;
		if ((financialAction == FinancialActionConstants.WRITEOFF)
				&& (type.equals(FinancialConstants.CREDIT)))
			return CategoryConstants.LOANTOCLIENTS;
		throw new FinancialException(FinancialExceptionConstants.ACTIONNOTFOUND);
	}

}
