package com.fizlrock.denly.Domain;

import java.sql.Timestamp;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Report
 */
@Embeddable
@Getter
@Setter
public class Report {
  // @Id
  // protected long Id;

  // @ManyToOne
  // protected User user;

  protected Timestamp created;

  Location location;

  // public void setUser(User u) {
  //   u.addReport(this);
  // }

}
