
public class ClientStarter {

    public static void main(String[] args) {
        int numOfThreads;
        int numOfIterations;
        String ipAddress;
        String port;

        if (args.length == 4) {
            numOfThreads = Integer.parseInt(args[0]);
            numOfIterations = Integer.parseInt(args[1]);
            ipAddress = args[2];
            port = args[3];
        } else {
            numOfThreads = 10;
            numOfIterations = 100;
            port = "8080";
            ipAddress = args[0];
        }

        Client client = new Client(numOfThreads,
            numOfIterations, ipAddress, port);
        try {
            client.roll();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
