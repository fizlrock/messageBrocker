package com.fizlrock.denly.Domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Location {

  protected String address;
  protected float latitude, longitude;

  @Enumerated(EnumType.STRING)
  protected LocationType type;

  public enum LocationType {
    IPAddress,
    GPS,
    Other
  }

}
