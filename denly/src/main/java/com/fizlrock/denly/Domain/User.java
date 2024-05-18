package com.fizlrock.denly.Domain;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected String id;

  @Column(nullable = false, unique = true)
  @Size(min = 8, max = 20)
  protected String username;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  @CreationTimestamp
  protected Date created;

  @Size(min = 8, max = 20)
  @NotNull
  protected String password;

  @Access(AccessType.FIELD)
  @OneToMany(mappedBy = "user")
  protected Set<Report> reports = new HashSet<>();

  @Transient
  private String some_info;

  public void addReport(Report r) {
    if (r == null)
      throw new NullPointerException("Report can't be null");
    if (r.getUser() != null)
      throw new IllegalStateException("Report is already assigned to user");
    r.setUser(this);
    reports.add(r);
  }

  protected void setReports(Set<Report> reports) {
  }

  public Set<Report> getReports() {
    return Collections.unmodifiableSet(reports);
  }
}
