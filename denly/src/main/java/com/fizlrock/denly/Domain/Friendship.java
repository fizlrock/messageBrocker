package com.fizlrock.denly.Domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Friendship
 */
@Getter
@Setter
public class Friendship {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @ManyToOne
  protected User sender;
  @ManyToOne
  protected User receiver;

  protected Timestamp created;
  protected boolean state;

  public boolean isActive() {
    return state;
  }

}
