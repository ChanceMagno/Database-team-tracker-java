import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Team {
  private String name;
  private int id;


  public Team(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Team> all() {
    String sql = "SELECT id, name FROM teams";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Team.class);
    }
  }

  public int getId(){
    return id;
  }

  public static Team find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM teams where id=:id";
      Team team = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Team.class);
      return team;
    }
  }

  public List<Member> getMembers() {
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM members where teamId=:id"; //teamId is col in sql
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Member.class);
    }

  }

  @Override
  public boolean equals(Object otherTeam){
  if (!(otherTeam instanceof Team)) {
    return false;
  } else {
    Team newTeam = (Team) otherTeam;
    return this.getName().equals(newTeam.getName()) && this.getId() == newTeam.getId();
    }
  }

public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO teams (name) VALUES (:name)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
  }
}


}
