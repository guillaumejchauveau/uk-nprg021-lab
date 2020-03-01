package ovh.gecu.nprg021_lab.p2;

@TesterInfo(createdBy = "Me", lastModified = "Aujourd'hui", priority = Priority.LOW)
public class MyTest {
  @Test
  public static void e() {

  }

  @Test(expectedExceptions = {RuntimeException.class})
  public void waw() {
    throw new RuntimeException();
  }

  public void a() {
    System.out.println("Don't run me");
  }

  @Test(enabled = false)
  public void b() {
    System.out.println("Don't run me i'm disabled");
  }

  @After
  public void d() {
    System.out.println("Run me after");
  }

  @Before
  public void c() {
    System.out.println("Run me before");
  }

  @Test
  public void f() {
    throw new RuntimeException();
  }
}
