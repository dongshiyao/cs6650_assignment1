import java.util.ArrayList;
import java.util.List;

public class Counter {
    private int requestGetSentCounter;
    private int requestPostSentCounter;
    private int requestGetSuccessCounter;
    private int requestPostSuccessCounter;
    private int responseGetCounter;
    private int responsePostCounter;
    private List<Long> latencyGet;
    private List<Long> latencyPost;

    public Counter() {
        this.requestGetSentCounter = 0;
        this.requestGetSuccessCounter = 0;
        this.responseGetCounter = 0;
        latencyGet = new ArrayList<>();
        latencyPost = new ArrayList<>();
    }

    public int getRequestGetSentCounter() {
        return requestGetSentCounter;
    }

    public int getResponseGetCounter() {
        return responseGetCounter;
    }

    public int getRequestGetSuccessCounter() {
        return requestGetSuccessCounter;
    }

    public int getRequestPostSentCounter() {
        return requestPostSentCounter;
    }

    public int getRequestPostSuccessCounter() {
        return requestPostSuccessCounter;
    }

    public int getResponsePostCounter() {
        return responsePostCounter;
    }

    public List<Long> getLatencyGet() {
        return latencyGet;
    }

    public List<Long> getLatencyPost() {
        return latencyPost;
    }

    public void requestGetSentIncrement() {
        requestGetSentCounter++;
    }

    public void requestGetSuccessIncrement() {
        requestGetSuccessCounter++;
    }

    public void responseGetIncrement() {
        responseGetCounter++;
    }

    public void addLatencyGet(long latency) {
        this.latencyGet.add(latency);
    }

    public void requestPostSentIncrement() {
        requestPostSentCounter++;
    }

    public void requestPostSuccessIncrement() {
        requestPostSuccessCounter++;
    }

    public void responsePostIncrement() {
        responsePostCounter++;
    }

    public void addLatencyPost(long latency) {
        this.latencyPost.add(latency);
    }
}
