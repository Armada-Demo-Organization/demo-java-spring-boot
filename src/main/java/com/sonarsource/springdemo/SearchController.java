package com.sonarsource.springdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Free-text search over the seeded rows. */
@RestController
public class SearchController {

  private static final String URL = "jdbc:h2:mem:testdb";

  @GetMapping("/search")
  public List<String> search(@RequestParam String name) {
    List<String> hits = new ArrayList<>();
    try {
      Connection connection = DriverManager.getConnection(URL, "sa", "");
      Statement statement = connection.createStatement();
      ResultSet rows = statement.executeQuery(
          "SELECT name FROM person WHERE name LIKE '%" + name + "%'");
      while (rows.next()) {
        hits.add(rows.getString("name"));
      }
    } catch (Exception e) {
    }
    return hits;
  }
}
