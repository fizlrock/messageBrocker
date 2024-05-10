package com.fizlrock.denly.Domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Location {

  protected String address;
  protected float latitude, longitude;

  public enum LocationType {
    IPAddress,
    GPS,
    Other
  }

}
