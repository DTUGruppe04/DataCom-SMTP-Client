import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to a mailserver and send one mail.
 *
 */
public class SMTPConnection {
    /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final int SMTP_PORT = 2552;
    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the 
       associated streams. Initialize SMTP connection. */
    public SMTPConnection(Envelope envelope) throws IOException {
        connection = new Socket("datacomm.bhsi.xyz",SMTP_PORT);
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toServer =  new DataOutputStream(connection.getOutputStream());

        if (parseReply(fromServer.readLine()) != 220) {
            throw new IOException();
        }

        /* Fill in */
	/* Read a line from server and check that the reply code is 220.
	   If not, throw an IOException. */
        /* Fill in */

	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
        String localhost = InetAddress.getLocalHost().getHostName();
        sendCommand("helo " + localhost, 250);

        isConnected = true;
    }

    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {
        sendCommand("mail from: <" + envelope.Sender + ">", 250);
        sendCommand("rcpt to: <" + envelope.Recipient + ">", 250);
        sendCommand("data", 354);
        toServer.writeBytes(envelope.Message + CRLF);
        sendCommand(".", 250);
        /* Fill in */
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
        /* Fill in */
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
        isConnected = false;
        try {
            sendCommand("quit", 221);
            connection.close();
        } catch (IOException e) {
            System.out.println("Unable to close connection: " + e);
            isConnected = true;
        }
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
        toServer.writeBytes(command + CRLF);
        int reply = parseReply(fromServer.readLine());
        if (reply != rc){
            throw new IOException("Error, reply code is: " + reply + " and should be: " + rc);
        }
        System.out.println("send success: " + command + reply);
        /* Fill in */
        /* Write command to server and read reply from server. */
        /* Fill in */

        /* Fill in */
	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
        /* Fill in */
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
        return Integer.parseInt(reply.substring(0,3));
        /* Fill in */
    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
        if(isConnected) {
            close();
        }
        super.finalize();
    }
}