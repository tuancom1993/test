    /*
     * Acton
     */
    private void saveClaimPayment(boolean isNewClaimFlag, ClaimCanonical claimCanonical, UserInfoForm userInfoForm) {
        List<com.aia.cmic.entity.ClaimPayment> listClaimPaymentEntity = claimPaymentRepository
                .findClaimPaymentByCompanyIdAndClaimNo(claimCanonical.getClaim().getCompanyId(), 
                        claimCanonical.getClaim().getClaimNo());
        List<ClaimPayment> listClaimPayment = claimCanonical.getClaimPayments();
        
        if(!isNewClaimFlag) {
            for (com.aia.cmic.entity.ClaimPayment claimPaymentUpdate : listClaimPaymentEntity) {
                boolean found = false;
                for (ClaimPayment claimPayment : listClaimPayment) {
                    if(claimPaymentUpdate.getClaimPaymentId() == claimPayment.getClaimPaymentId()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    claimPaymentRepository.deleteClaimPaymentByCompanyIdAndClaimNo(claimPaymentUpdate.getCompanyId(),
                            claimPaymentUpdate.getClaimNo());
                }
            }
        }

        if (listClaimPayment != null && listClaimPayment.size() > 0) {
            for (ClaimPayment claimPayment : listClaimPayment) {
                if (claimPayment.getClaimPaymentId() == null) {
                    com.aia.cmic.entity.ClaimPayment claimPaymentEntity = new com.aia.cmic.entity.ClaimPayment();
                    copyProperties(claimPayment, claimPaymentEntity);
                    claimPaymentEntity.setLastModifiedBy(userInfoForm.getUserId());
                    claimPaymentEntity.setLastModifiedDt(new Date());
                    //This one need set data??
                    claimPaymentEntity.setLastModifiedUserDept("");
                    //This one need set data??
                    claimPaymentEntity.setLastModifiedUserDesk("");
                    claimPaymentEntity.setClaimNo(claimCanonical.getClaim().getClaimNo());
                    claimPaymentEntity.setOccurrence(claimCanonical.getClaim().getOccurrence());
                    claimPaymentEntity.setCreatedBy(userInfoForm.getUserId());
                    claimPaymentEntity.setCreatedDt(new Date());
                    claimPaymentRepository.saveClaimPayment(claimPaymentEntity);
                } else {
                    for (com.aia.cmic.entity.ClaimPayment claimPaymentUpdate : listClaimPaymentEntity) {
                        if (claimPaymentUpdate.getClaimPaymentId()
                                .equals(claimPayment.getClaimPaymentId())) {
                            copyProperties(claimPayment, claimPaymentUpdate);
                            claimPaymentUpdate.setLastModifiedBy(userInfoForm.getUserId());
                            claimPaymentUpdate.setLastModifiedDt(new Date());
                            claimPaymentRepository.saveClaimPayment(claimPaymentUpdate);
                        }
                    }
                }
            }
        }
    }
    /*
     * Acton
     */
    private void saveInsured(boolean isNewClaimFlag, ClaimCanonical claimCanonical, UserInfoForm userInfoForm) {
        String companyId = claimCanonical.getClaim().getCompanyId();
        String claimNo = claimCanonical.getClaim().getClaimNo();
        List<com.aia.cmic.entity.Insured> listInsuredEntity = insuredRepository
                .findInsuredByCompanyIdAndClaimNo(companyId, claimNo);
        List<ClaimPolicyCanonical> listClaimPolicy = claimCanonical.getClaimPolicies();
        
        if(!isNewClaimFlag) {
            for (com.aia.cmic.entity.Insured insuredUpdate : listInsuredEntity) {
                boolean found = false;
                for (ClaimPolicyCanonical claimPolicyCanonical : listClaimPolicy) {
                    if(insuredUpdate.getInsuredId() == claimPolicyCanonical.getInsured().getInsuredId()){
                        found = true;
                        break;
                    }
                }
                
                if(!found) {
                    insuredRepository.deleteInsuredByCompanyIdAndClaimNo(companyId, claimNo);
                }
            }
        }
        
        if (listClaimPolicy != null && listClaimPolicy.size() > 0) {
            if (isNewClaimFlag){
                for (ClaimPolicyCanonical claimPolicyCanonical : listClaimPolicy) {
                    Insured insured = claimPolicyCanonical.getInsured();
                    if (insured.getInsuredId() == null) {
                        com.aia.cmic.entity.Insured insuredEntity = new com.aia.cmic.entity.Insured();
                        copyProperties(insured, insuredEntity);
                        insuredEntity.setLastModifiedBy(userInfoForm.getUserId());
                        insuredEntity.setLastModifiedDt(new Date());
                        insuredEntity.setClaimNo(claimNo);
                        insuredEntity.setOccurrence(claimCanonical.getClaim().getOccurrence());
                        insuredEntity.setCreatedBy(userInfoForm.getUserId());
                        insuredEntity.setCreatedDt(new Date());
                        insuredRepository.saveInsured(insuredEntity);
                    } else {
                        for (com.aia.cmic.entity.Insured insuredUpdate : listInsuredEntity) {
                            if (insuredUpdate.getInsuredId().equals(insured.getInsuredId())) {
                                copyProperties(insured, insuredUpdate);
                                insuredUpdate.setLastModifiedBy(userInfoForm.getUserId());
                                insuredUpdate.setLastModifiedDt(new Date());
                                insuredRepository.saveInsured(insuredUpdate);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Acton
     */
    private void savePayee(boolean isNewClaimFlag, ClaimCanonical claimCanonical, UserInfoForm userInfoForm) {
        String companyId = claimCanonical.getClaim().getCompanyId();
        String claimNo = claimCanonical.getClaim().getClaimNo();
        List<com.aia.cmic.entity.Payee> listPayeeEntity = payeeRepository
                .findPayeeByCompanyIdAndClaimNo(companyId, claimNo);
        List<ClaimPolicyCanonical> listClaimPolicy = claimCanonical.getClaimPolicies();
        
        if(!isNewClaimFlag){
            for (com.aia.cmic.entity.Payee payeeUpdate : listPayeeEntity) {
                boolean found = false;
                for (ClaimPolicyCanonical claimPolicyCanonical : listClaimPolicy) {
                    if(payeeUpdate.getPayeeId() == claimPolicyCanonical.getPayee().getPayeeId()) {
                        found = true;
                        break;
                    }
                }
                
                if(!found) {
                    payeeRepository.deletePayeeByCompanyIdAndClaimNo(companyId, claimNo);
                }
            }
        }
        
        if (listClaimPolicy != null && listClaimPolicy.size() > 0) {
            if (isNewClaimFlag) {
                for (ClaimPolicyCanonical claimPolicyCanonical : listClaimPolicy) {
                    Payee payeeModel = claimPolicyCanonical.getPayee();
                    if (payeeModel.getPayeeId() == null) {
                        com.aia.cmic.entity.Payee payeeEntity = new com.aia.cmic.entity.Payee();
                        copyProperties(payeeModel, payeeEntity);
                        payeeEntity.setLastModifiedBy(userInfoForm.getUserId());
                        payeeEntity.setLastModifiedDt(new Date());
                        payeeEntity.setClaimNo(claimNo);
                        payeeEntity.setOccurrence(claimCanonical.getClaim().getOccurrence());
                        payeeEntity.setCreatedBy(userInfoForm.getUserId());
                        payeeEntity.setCreatedDt(new Date());
                        payeeRepository.savePayee(payeeEntity);
                    } else {
                        for (com.aia.cmic.entity.Payee payeeUpdate : listPayeeEntity) {
                            if(payeeUpdate.getPayeeId().equals(payeeModel.getPayeeId())){
                                copyProperties(payeeModel, payeeUpdate);
                                payeeUpdate.setLastModifiedBy(userInfoForm.getUserId());
                                payeeUpdate.setLastModifiedDt(new Date());
                                payeeRepository.savePayee(payeeUpdate);
                            }
                        }
                    }
                }
            }
        }
    }
