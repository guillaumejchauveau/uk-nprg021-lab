package ovh.gecu.uk_nprg021_lab.jdbc_assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Agent {
  protected String code;
  protected String name;
  protected String workingArea;
  protected Collection<Customer> customers;

  static public Agent fromResultSet(ResultSet resultSet) throws SQLException {
    return new Agent(
      resultSet.getString("AGENTS.AGENT_CODE"),
      resultSet.getString("AGENTS.AGENT_NAME"),
      resultSet.getString("AGENTS.WORKING_AREA"));
  }

  public Agent(String code, String name, String workingArea) {
    this.code = code;
    this.name = name;
    this.workingArea = workingArea;
    this.customers = new ArrayList<>();
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public String getWorkingArea() {
    return this.workingArea;
  }

  public Collection<Customer> getCustomers() {
    return this.customers;
  }
}
