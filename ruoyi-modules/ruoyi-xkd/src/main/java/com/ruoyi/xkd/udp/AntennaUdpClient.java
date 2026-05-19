package com.ruoyi.xkd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.stereotype.Component;

@Component
public class AntennaUdpClient
{
    public void send(String ip, Integer port, byte[] frame) throws Exception
    {
        if (ip == null || ip.isEmpty())
        {
            throw new IllegalArgumentException("设备 IP 不能为空");
        }

        if (port == null)
        {
            throw new IllegalArgumentException("设备端口不能为空");
        }

        if (frame == null || frame.length == 0)
        {
            throw new IllegalArgumentException("发送帧不能为空");
        }

        try (DatagramSocket socket = new DatagramSocket())
        {
            DatagramPacket packet = new DatagramPacket(
                    frame,
                    frame.length,
                    InetAddress.getByName(ip),
                    port
            );

            socket.send(packet);
        }
    }

    public void broadcast(Integer port, byte[] frame) throws Exception
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            socket.setBroadcast(true);

            DatagramPacket packet = new DatagramPacket(
                    frame,
                    frame.length,
                    InetAddress.getByName("255.255.255.255"),
                    port
            );

            socket.send(packet);
        }
    }
}