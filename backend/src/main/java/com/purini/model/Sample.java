package com.purini.model;

import com.purini.model.pojos.AuditData;
import com.purini.model.pojos.Auditable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@Data
@ToString
@Document(collection = "sample")
public class Sample implements Identifiable, Auditable {

    @Id
    private Long id;
    private String name;
    private Boolean active;
    private Date startDate;
    private AuditData auditData;

}
