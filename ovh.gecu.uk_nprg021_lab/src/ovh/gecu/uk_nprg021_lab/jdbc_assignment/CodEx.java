package ovh.gecu.uk_nprg021_lab.jdbc_assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class CodEx {
  private static final String H2_MEM_URL = "jdbc:h2:mem:default";

  /**
   * Instantiate the H2 in-memory database and load it with the schema and data from provided scripts.
   * <p>
   * The database will stay in the memory and keep its content until JVM is terminated.
   *
   * @param schemaScript sql script containing ddl commands to create database schema
   * @param dataScript   sql script containing commands to insert data into tables
   * @throws SQLException sql exception
   */
  private static void prepareDatabase(String schemaScript, String dataScript) throws SQLException {
    Connection con = DriverManager.getConnection(
      String.format("%s;DB_CLOSE_DELAY=-1;INIT=runscript from '%s'\\;runscript from '%s'",
        H2_MEM_URL, schemaScript, dataScript));
    con.close();
  }

  public static void main(String[] args) throws SQLException {
    var schemaScript = args[0];
    var dataScript = args[1];
    var location = args[2];
    prepareDatabase(schemaScript, dataScript);

    var conn = DriverManager.getConnection(H2_MEM_URL);
    var stmt = conn.prepareStatement("SELECT * FROM AGENTS LEFT JOIN CUSTOMER ON AGENTS.AGENT_CODE = CUSTOMER.AGENT_CODE WHERE AGENTS.WORKING_AREA = ? GROUP BY AGENTS.AGENT_NAME, CUSTOMER.CUST_CODE ORDER BY AGENTS.AGENT_NAME, CUSTOMER.CUST_NAME");
    stmt.setString(1, location);
    var rs = stmt.executeQuery();

    var agents = new ArrayList<Agent>();
    var totalCustomer = 0;
    Agent previousAgent = null;
    while (rs.next()) {
      if (previousAgent == null || !previousAgent.getName().equals(rs.getString("AGENT_NAME"))) {
        previousAgent = Agent.fromResultSet(rs);
        agents.add(previousAgent);
      }
      if (rs.getString("CUSTOMER.CUST_NAME") != null) {
        previousAgent.getCustomers().add(Customer.fromResultSet(rs, previousAgent));
        totalCustomer++;
      }
    }

    if (agents.isEmpty()) {
      System.out.println("No agents in " + location);
      return;
    }

    System.out.println(agents.size() + " agents in " + location + ", " + totalCustomer + " customers");
    for (var agent : agents) {
      System.out.println("Agent: " + agent.getName());
      for (var customer : agent.getCustomers()) {
        System.out.println("  Customer: " + customer.getName() + ", Phone: " + customer.getPhoneNumber());
      }
    }
    System.out.println();
  }
}
