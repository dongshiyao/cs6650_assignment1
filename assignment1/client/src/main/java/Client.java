
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private ArrayList<ClientTask> listOfClientTask;
    private ExecutorService executorService;

    public Client(int numOfThread, int numOfIteration, String ip, String port) {
        listOfClientTask = new ArrayList<>();
        for (int i = 0; i < numOfThread; i++) {
            ClientTask clientTask = new ClientTask(ip, port, numOfIteration);
            listOfClientTask.add(clientTask);
            executorService = Executors.newFixedThreadPool(numOfThread);
            executorService = Executors.newCachedThreadPool();
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Client Tasks Start");
        System.out.println("------------------------------------------------------------");
        System.out.println("Number of Threads: " + numOfThread);
        System.out.println("Number of iterations: " + numOfIteration);
        System.out.println("IP Address: " + ip);
        System.out.println("Port: " + port);
    }

    public void roll() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("------------------------------------------------------------");
        System.out.println("Report");
        System.out.println("------------------------------------------------------------");
        System.out.println("Client start time: " + startTime + "ms");
        System.out.println("All threads running...");
        executorService.invokeAll(listOfClientTask);
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("All threads complete time: " + endTime);
        System.out.println("Total number of requests sent: "
            + (sumUpSentGetRequest(listOfClientTask) + sumUpSentPostRequest(listOfClientTask)));
        System.out.println("Total number of successful responses: "
            + (sumUpGetResponse(listOfClientTask) + sumUpPostResponse(listOfClientTask)));
        System.out.println(String.format("Test Wall Time: %.1fs", (endTime - startTime) / 1000.0));
        System.out.println("------------------------------------------------------------");
        System.out.println("stats for GET request");
        System.out.println("------------------------------------------------------------");
        System.out.println("Total number of GET requests sent: "
            + sumUpSentGetRequest(listOfClientTask));
        System.out.println("Total number of successful GET requests: "
            + sumUpSuccessGetRequest(listOfClientTask));
        System.out.println("Total number of successful GET responses: "
            + sumUpGetResponse(listOfClientTask));
        List<Long> allGetLatencies = cleanGetLatencyData(listOfClientTask);
        System.out.println(String.format("Median latency of all GET requests: %dms",
            getMedianLatency(allGetLatencies)));
        System.out.println(String.format("Mean latency of all GET requests: %dms",
            getMeanLatency(allGetLatencies)));
        System.out.println(String.format("99th percentile latency of GET request: %dms",
            getPercentileLatency(allGetLatencies, 0.99)));
        System.out.println(String.format("95th percentile latency of GET request: %dms",
            getPercentileLatency(allGetLatencies, 0.95)));
        System.out.println("------------------------------------------------------------");
        System.out.println("stats for POST request");
        System.out.println("------------------------------------------------------------");
        System.out.println("Total number of POST requests sent: "
            + sumUpSentPostRequest(listOfClientTask));
        System.out.println("Total number of successful POST requests: "
            + sumUpSuccessPostRequest(listOfClientTask));
        System.out.println("Total number of successful POST responses: "
            + sumUpPostResponse(listOfClientTask));
        List<Long> allPostLatencies = cleanPostLatencyData(listOfClientTask);
        System.out.println(String.format("Median latency of all POST requests: %dms",
            getMedianLatency(allPostLatencies)));
        System.out.println(String.format("Mean latency of all POST requests: %dms",
            getMeanLatency(allPostLatencies)));
        System.out.println(String.format("99th percentile latency of POST request: %dms",
            getPercentileLatency(allPostLatencies, 0.99)));
        System.out.println(String.format("95th percentile latency of POST request: %dms",
            getPercentileLatency(allPostLatencies, 0.95)));

    }

    private int sumUpSentGetRequest(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getRequestGetSentCounter();
        }
        return counter;
    }

    private int sumUpSentPostRequest(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getRequestPostSentCounter();
        }
        return counter;
    }

    private int sumUpSuccessGetRequest(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getRequestGetSuccessCounter();
        }
        return counter;
    }

    private int sumUpSuccessPostRequest(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getRequestPostSuccessCounter();
        }
        return counter;
    }

    private int sumUpGetResponse(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getResponseGetCounter();
        }
        return counter;
    }

    private int sumUpPostResponse(List<ClientTask> listOfClientTask) {
        int counter = 0;
        for (ClientTask aListOfClientTask : listOfClientTask) {
            counter += aListOfClientTask.getCounter().getResponsePostCounter();
        }
        return counter;
    }

    private List<Long> cleanGetLatencyData(List<ClientTask> listOfClientTask) {
        List<Long> result = new ArrayList<>();
        for (ClientTask clientTask : listOfClientTask) {
            result.addAll(clientTask.getCounter().getLatencyGet());
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    private List<Long> cleanPostLatencyData(List<ClientTask> listOfClientTask) {
        List<Long> result = new ArrayList<>();
        for (ClientTask clientTask : listOfClientTask) {
            result.addAll(clientTask.getCounter().getLatencyPost());
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    private Long getMeanLatency(List<Long> latencies) {
        long sum = 0;
        for (int i = 0; i< latencies.size(); i++) {
            sum += latencies.get(i);
        }
        return sum / latencies.size();
    }

    private Long getMedianLatency(List<Long> latencies) {
        return latencies.get(latencies.size() / 2);
    }

    private Long getPercentileLatency(List<Long> latencies, double percentile) {
        int index = new Double(Math.floor(percentile * latencies.size())).intValue();
        return latencies.get(index);
    }
}
