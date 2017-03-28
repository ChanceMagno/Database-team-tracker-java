import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class TeamTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/team_tracker_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteMembersQuery = "DELETE FROM members *;";
      String deleteTeamsQuery = "DELETE FROM teams *;";
      con.createQuery(deleteMembersQuery).executeUpdate();
      con.createQuery(deleteTeamsQuery).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfNameAretheSame() {
    Team firstTeam = new Team("name");
    Team secondTeam = new Team("name");
    assertTrue(firstTeam.equals(secondTeam));
  }

  @Test
 public void save_savesIntoDatabase_true() {
   Team myTeam = new Team("name");
   myTeam.save();
   assertTrue(Team.all().get(0).equals(myTeam));
 }

  @Test
  public void Team_instantiatesCorrectly_true() {
    Team newTeam = new Team("name");
    assertEquals(true, newTeam instanceof Team);
  }

  @Test
  public void getName_getsNameValue_name() {
    Team newTeam = new Team("name");
    assertEquals("name", newTeam.getName());
  }

  @Test
  public void all_getsAllTeams_true() {
    Team firstTeam = new Team("name");
    firstTeam.save();
    Team secondTeam = new Team("name");
    secondTeam.save();
    assertEquals(true, Team.all().get(0).equals(firstTeam));
    assertEquals(true, Team.all().get(1).equals(secondTeam));
  }

  @Test
  public void save_assignsIdToObject() {
    Team myTeam = new Team("name");
    myTeam.save();
    Team savedTeam = Team.all().get(0);
    assertEquals(myTeam.getId(), savedTeam.getId());
  }

  @Test
  public void clear_emptiesAllTeamsFromArrayList(){
    assertEquals(0, Team.all().size());
  }

  @Test
  public void getId_TeamInstantiatesWithId_1() {
    Team newTeam = new Team("name");
    newTeam.save();
    assertTrue(newTeam.getId() > 0);
  }

  @Test
  public void find_returnsTeamWithSameId_secondTeam() {
  Team firstTeam = new Team("name");
  firstTeam.save();
  Team secondTeam = new Team("name");
  secondTeam.save();
  assertEquals(Team.find(secondTeam.getId()), secondTeam);
  }

  @Test
  public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
    Team newTeam = new Team("name");
    newTeam.save();
    Member firstMember = new Member ("name", newTeam.getId());
    firstMember.save();
    Member secondMember = new Member ("sam", newTeam.getId());
    secondMember.save();
    Member[] members = new Member [] {firstMember, secondMember};
    assertTrue(newTeam.getMembers().containsAll(Arrays.asList(members)));

  }
}
