package com.fizlrock.denly.Domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Friendship
 */
@Entity
@Getter
public class Friendship {
  private Friendship() {
  }

  public Friendship(User sender, User receiver) {
    this.id.senderId = sender.id;
    this.id.receiverId = receiver.id;

    this.sender = sender;
    this.receiver = receiver;

    sender.getFriendships().add(this);
    receiver.getFriendships().add(this);
    this.state = FriendshipState.Requested;
  }

  @Embeddable
  @EqualsAndHashCode
  public static class Id implements Serializable {
    @Column(name = "SENDER_ID")
    protected String senderId;
    @Column(name = "RECEIVER_ID")
    protected String receiverId;
  }

  @EmbeddedId
  protected Id id = new Id();

  @Column(updatable = false)
  protected Date created = new Date();

  @ManyToOne
  @JoinColumn(name = "SENDER_ID", updatable = false, insertable = false)
  protected User sender;

  @ManyToOne
  @JoinColumn(name = "RECEIVER_ID", updatable = false, insertable = false)
  protected User receiver;

  @Enumerated(EnumType.STRING)
  protected FriendshipState state;

  public enum FriendshipState {
    Requested,
    Confirmed,
    Canceled
  }

}
