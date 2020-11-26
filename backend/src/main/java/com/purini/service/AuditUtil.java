package com.purini.service;

import com.purini.model.pojos.AuditData;
import com.purini.model.pojos.Auditable;

import java.util.Date;

public class AuditUtil {

    public static void setAuditData(Auditable auditable, String username, Date date) {
        if (auditable.getAuditData() == null) {
            auditable.setAuditData(new AuditData());
        }
        auditable.getAuditData().setCreatedBy(username);
        auditable.getAuditData().setCreatedDate(date);
        auditable.getAuditData().setUpdatedBy(username);
        auditable.getAuditData().setUpdatedDate(date);
    }

}
