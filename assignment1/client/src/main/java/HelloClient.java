import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class HelloClient {

  public void getStatus() {
    Client client = ClientBuilder.newClient();
//    Response response = client.target("http://localhost:8080/rest/hello/status").request().get(String.class);
    long start = System.currentTimeMillis();
    Response response = client.target("http://54.244.85.135:8080/assignment1_war/rest/hello").request().post(Entity.entity("test", MediaType.TEXT_PLAIN_TYPE));
    Response response1 = client.target("http://54.244.85.135:8080/assignment1_war/rest/hello/status").request().get();
    client.close();
    long end = System.currentTimeMillis();
    System.out.println(String.format("Wall Time: %.2fs", (end - start) / 1000.00));
//    System.out.println(response.getRequest());
//    System.out.println(response1.getRequest());
  }


  public static void main(String[] args) {
    new HelloClient().getStatus();
  }
}
