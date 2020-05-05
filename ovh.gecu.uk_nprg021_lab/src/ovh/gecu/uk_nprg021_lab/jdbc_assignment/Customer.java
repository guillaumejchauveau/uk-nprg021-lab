package ovh.gecu.uk_nprg021_lab.jdbc_assignment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
  protected String name;
  protected String phoneNumber;
  protected Agent agent;

  public static Customer fromResultSet(ResultSet resultSet, Agent agent) throws SQLException {
    return new Customer(
      resultSet.getString("CUSTOMER.CUST_NAME"),
      resultSet.getString("CUSTOMER.PHONE_NO"),
      agent);
  }

  public Customer(String name, String phoneNumber, Agent agent) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.agent = agent;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Agent getAgent() {
    return agent;
  }
}
