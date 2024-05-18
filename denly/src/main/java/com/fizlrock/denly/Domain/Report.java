package com.fizlrock.denly.Domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;

// @org.hibernate.annotations.Immutable
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Report
 */
@Entity
@Getter
@Setter
public class Report {
  @Id
  protected long Id;

  @ManyToOne
  protected User user;

  protected Timestamp created;

  Location location;

  public void setUser(User u) {
    u.addReport(this);
  }

}
