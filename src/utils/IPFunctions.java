package utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author sumit
 * 
 * Class: IPFunctions
 * 
 * Class containing basic functions related to IP address.
 */
public class IPFunctions {

    /**
     * Fetches the IP address of the current machine.
     * Returns non-loopback address so that 127.0.0.1 is not returned in linux.
     * @return IP address of the current machine
     * @throws SocketException 
     */
    public static InetAddress getFirstNonLoopbackAddress() throws SocketException {
        boolean preferIpv4 = true;
        boolean preferIPv6 = false;
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
}
