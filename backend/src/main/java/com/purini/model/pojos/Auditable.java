package com.purini.model.pojos;

public interface Auditable {

    AuditData getAuditData();
    void setAuditData(AuditData auditData);
}
