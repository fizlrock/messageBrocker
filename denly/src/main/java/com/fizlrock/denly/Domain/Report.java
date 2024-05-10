package com.fizlrock.denly.Domain;

import jakarta.persistence.Entity;

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

  // @ManyToOne
  // protected User user;

  Location location;
}
