package ccs.neu.edu.cs6650.assignment1;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloServer {
  // http://localhost:8080/rest/hello/status

  @GET
  @Path("/status")
  @Produces(MediaType.TEXT_PLAIN)
  public String getStatus() {
    return "alive";
  }

  @GET
  @Path("/year")
  @Produces(MediaType.TEXT_PLAIN)
  public String getYear() {
    return "2017";
  }

  @GET
  @Path("/echo")
  public Response getResponse(@QueryParam("message") String msg) {
    return Response.ok("You get the message: " + msg).build();
  }


  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  public int postText(String content) {
    return (content.length());
  }

}