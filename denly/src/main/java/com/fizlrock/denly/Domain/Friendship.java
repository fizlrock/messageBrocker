package com.fizlrock.denly.Domain;

import java.sql.Timestamp;

import org.hibernate.type.YesNoConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Friendship
 */
@Entity
@Getter
@Setter
public class Friendship {
  // @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  // protected Long id;

  @ManyToOne
  @Id
  protected User sender;
  @Id
  @ManyToOne
  protected User receiver;

  protected Timestamp created;

  @Enumerated(EnumType.STRING)
  protected FriendshipState state;

  public enum FriendshipState {
    Requested,
    Confirmed,
    Canceled
  }

}
