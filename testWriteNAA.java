	@Test
	public void testWriteNAA() {
		//TODO
		try {
			UserInfoForm userInfoForm = new UserInfoForm();
			userInfoForm.setUserId("0001");
			userInfoForm.setUserName("Admin");

			ClaimCanonical test = new ClaimCanonical();
			test.setClaim(createClaim());
			test.setClaimPayments(getListClaimPaymentGroupBy());
			test.getClaimPayments().get(0).setProductType(ProductType.PA.name());
			test.getClaimPayments().get(1).setProductType(ProductType.PA.name());

			test.setClaimPolicies(getClaimPolicyCanonicals());
			test.getClaimPolicies().get(0).getClaimPolicy().setAgentPolicyInd("Y");
			test.getClaimPolicies().get(0).getClaimPolicy().setBusinessLine(BusinessLine.CS.name());
			test.getClaimPolicies().get(1).getClaimPolicy().setAgentPolicyInd("Y");
			test.getClaimPolicies().get(1).getClaimPolicy().setBusinessLine(BusinessLine.CS.name());

			test = claimService.writeClaim(test, "C", "", userInfoForm);
			ClaimCanonical claimCanonical = claimService.retrieveClaimDetail("TH", test.getClaim().getClaimNo());
			claimCanonical.getClaimPayments().get(0).setPaymentStatus("40");
			claimCanonical.getClaimPayments().get(0).setPayeeType("ABC");
			claimCanonical.getClaimPayments().get(1).setPaymentStatus("40");
			claimCanonical.getClaimPayments().get(1).setPayeeType("ABC");
			test = claimService.writeClaim(claimCanonical, "U", "", userInfoForm);

			List<Account> listAccount = getAccount();

			for (Account account : listAccount) {
				accountRepository.saveAccount(account);
			}

			for (Plan planSave : getPlans()) {
				planRepository.savePlan(planSave);
			}
			List<Plan> plans = planRepository.findAllPlans();
			//cycleDateRepository.saveCycleDate(getCycleDate());
	
			settlementService.settleClaim("TH", claimCanonical, userInfoForm);

			List<NaaWorking> listNAAFindAll = naaWorkingRepository.findAllNaaWorking();
			
			assertEquals("G000089TL", listNAAFindAll.get(0).getCsFormatPolicyNo());
			
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}
