package com.purini.model.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AuditData {

  //@CreatedBy
  private String createdBy;
  //@CreatedDate
  private Date createdDate;
  //@LastModifiedBy
  private String updatedBy;
  //@LastModifiedDate
  private Date updatedDate;

}
