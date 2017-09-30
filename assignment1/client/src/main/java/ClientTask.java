import java.util.concurrent.Callable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientTask implements Callable<Counter> {

    private String getEndpoint;
    private String postEndpoint;
    private int iterations;
    private Counter counter;
    private Client client;

    public ClientTask(String address, String port, int iterations) {
        this.getEndpoint = "http://" + address + ":" + port + "/assignment1_war/rest/hello/status";
        this.postEndpoint = "http://" + address + ":" + port + "/assignment1_war/rest/hello";
        this.iterations = iterations;
        this.client = ClientBuilder.newClient();
        this.counter = new Counter();
    }

    public Counter getCounter() {
        return counter;
    }

    public void getRequest() {
        try {
            counter.requestGetSentIncrement();
            long start = System.currentTimeMillis();
            Response response = client.target(getEndpoint).request().get();
            response.close();
            counter.requestGetSuccessIncrement();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                counter.responseGetIncrement();
            }
            counter.addLatencyGet(System.currentTimeMillis() - start);
        } catch (Exception ignored) {
        }
    }

    public void postRequest() {
        try {
            counter.requestPostSentIncrement();
            long start = System.currentTimeMillis();
            Response response = client.target(postEndpoint).request().post(Entity.entity("test", MediaType.TEXT_PLAIN_TYPE));
            response.close();
            counter.requestPostSuccessIncrement();
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                counter.responsePostIncrement();
            }
            counter.addLatencyPost(System.currentTimeMillis() - start);
        } catch (Exception ignored) {
        }
    }

    public Counter call() throws Exception {
        for (int i = 0; i < iterations; i++) {
            getRequest();
            postRequest();
        }
        client.close();
        return counter;
    }
}
