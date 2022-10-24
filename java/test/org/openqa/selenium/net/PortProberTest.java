package org.openqa.selenium.net;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UnitTests")
public class PortProberTest {

    private static final int TEST_PORT = 12345;

    @Test
    void checkPortIsFree_checksIpv4Localhost() throws Exception {
        try (ServerSocket socket = new ServerSocket()) {
            socket.bind(new InetSocketAddress("localhost", TEST_PORT));
            assertThat(PortProber.checkPortIsFree(TEST_PORT)).isEqualTo(-1);
        }
    }

    @Test
    void checkPortIsFree_checksIpv4AllInterfaces() throws Exception {
        try (ServerSocket socket = new ServerSocket()) {
            socket.bind(new InetSocketAddress("0.0.0.0", TEST_PORT));
            assertThat(PortProber.checkPortIsFree(TEST_PORT)).isEqualTo(-1);
        }
    }

    @Test
    void checkPortIsFree_checksIpv6Localhost() throws Exception {
        if (!Platform.getCurrent().is(Platform.LINUX)) {
            try (ServerSocket socket = new ServerSocket()) {
                socket.bind(new InetSocketAddress("::1", TEST_PORT));
                assertThat(PortProber.checkPortIsFree(TEST_PORT)).isEqualTo(-1);
            }
        }
    }

    @Test
    void checkPortIsFree_checksIpv6AllInterfaces() throws Exception {
        try (ServerSocket socket = new ServerSocket()) {
            socket.bind(new InetSocketAddress("::", TEST_PORT));
            assertThat(PortProber.checkPortIsFree(TEST_PORT)).isEqualTo(-1);
        }
    }
}
