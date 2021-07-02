package pl.mwewerek.rough;

import pl.mwewerek.utilities.MailConfig;
import pl.mwewerek.utilities.MonitoringMail;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.math.MathContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Playground {

    public static void main(String[] args) throws UnknownHostException, MessagingException {

        // get local host address
        String localHostAddress = InetAddress.getLocalHost().getHostAddress();
        String messageBody = "http://" + localHostAddress + ":8080/job/DataDrivenFrameworkProject/Extent_20Report/";
        System.out.println(messageBody);

        MonitoringMail mail = new MonitoringMail();
        mail.sendMail(MailConfig.server, MailConfig.from, MailConfig.to, MailConfig.subject, messageBody);
    }
}
