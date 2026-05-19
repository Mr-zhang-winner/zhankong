package com.ruoyi.xkd.protocol.constants;

/**
 * 主机与天线接口协议常量
 */
public class AntennaProtocolConstants
{
    private AntennaProtocolConstants() {}

    public static final int FRAME_FLAG = 0x7E;
    public static final int ESCAPE = 0x7D;
    public static final int ESCAPE_7E = 0x5E;
    public static final int ESCAPE_7D = 0x5D;

    /**控制层命令*/
    public static final int CMD_SET = 0x80;
    public static final int CMD_SET_ACK = 0x81;
    public static final int CMD_QUERY = 0x82;
    public static final int CMD_QUERY_ACK = 0x83;

    /**应用层命令*/
    public static final int CMD_REPORT = 0x84;
    public static final int CMD_REPORT_ACK = 0x85;
    public static final int CMD_FAC_QUERY = 0x86;
    public static final int CMD_FAC_QUERY_ACK = 0x87;
    public static final int CMD_BRD_SEARCH = 0x88;
    public static final int CMD_BRD_SEARCH_ACK = 0x89;
    public static final int CMD_EPHES_SET = 0x8A;
    public static final int CMD_EPHES_SET_ACK = 0x8B;

    /**写入参数KEY */
    public static final int KEY_CHANGE_SAT = 0x01;
    public static final int KEY_SEND_FREQ = 0x02;
    public static final int KEY_SBWP_FREQ = 0x03;
    public static final int KEY_RECV_FREQ = 0x04;
    public static final int KEY_RBWP_FREQ = 0x05;
    public static final int KEY_BUC_DECAY = 0x06;
    public static final int KEY_BDC_DECAY = 0x07;
    public static final int KEY_ANT_SWITCH = 0x08;
    public static final int KEY_POL_CONFIG = 0x09;
    public static final int KEY_ANT_RSRP = 0x0C;
    public static final int KEY_ANT_ANGLE = 0x0D;
    public static final int KEY_ANT_TX_POL = 0x11;
    public static final int KEY_ANT_RX_POL = 0x12;
    public static final int KEY_SAT_NO = 0x13;
    public static final int KEY_BEACON_FREQ = 0x1C;

    /**读取参数KEY */
    public static final int KEY_ANT_STATUS = 0x0A;
    public static final int KEY_ANT_TYPE = 0x0B;
    public static final int KEY_ANT_PAR_STATUS = 0x0E;
    public static final int KEY_ANT_TEMP = 0x14;
    public static final int KEY_ACU_STATUS = 0x20;
    public static final int KEY_RF_STATUS = 0x21;
    public static final int KEY_BEACON_STATUS = 0x22;
    public static final int KEY_KABUC_POWER = 0x23;
    public static final int KEY_KABUC_TEMP = 0x24;
    public static final int KEY_KABUC_UP_LOCAL = 0x25;
    public static final int KEY_KABUC_DOWN_LOCAL = 0x26;
    public static final int KEY_BEACON_RSRP = 0x31;
    public static final int KEY_BEACON_FREQ_QUERY = 0x32;
    public static final int KEY_BEACON_SNR = 0x33;
    public static final int KEY_ANT_WORK_STATUS = 0x34;
    public static final int KEY_LOCK_STATUS = 0x35;
    public static final int KEY_TOD = 0x36;
    public static final int KEY_ANT_CAP = 0x46;
    public static final int KEY_SEND_GAIN = 0x47;
    public static final int KEY_BUC_RANGE = 0x48;
    public static final int KEY_RECV_GAIN = 0x49;
    public static final int KEY_BDC_RANGE = 0x4A;

    /**出厂信息KEY */
    public static final int KEY_DEVICE_NAME = 0x6E;
    public static final int KEY_DEVICE_OEM = 0x6F;
    public static final int KEY_DEVICE_SN = 0x70;
    public static final int KEY_DEVICE_DATE = 0x71;
    public static final int KEY_SOFT_VERSION = 0x72;

    /**应用层主动上报KEY */
    public static final int KEY_SELF_CHECK_READY = 0x81;
    public static final int KEY_REPORT_TOD = 0x82;
    public static final int KEY_PARABOLA_REACH = 0x83;

    /** ACK状态码*/
    public static final int PARAM_SET_OK = 0x20;
    public static final int PARAM_SET_FAILED = 0x21;
    public static final int PARAM_SET_TIMEOUT = 0x22;
    public static final int PARAM_SET_UNKNOWN = 0x23;
    public static final int PARAM_SET_RECEIVED = 0x24;
    public static final int PARAM_SET_INVALID = 0x25;
    public static final int PARAM_SET_ERROR = 0x26;
    public static final int PARAM_SET_NOT_READY = 0x27;
    public static final int PARAM_SET_REFUSED = 0x28;
    public static final int PARAM_FRAME_ERROR = 0x29;

    /**广播搜索*/
    public static final String BROADCAST_IP = "255.255.255.255";
    public static final int BROADCAST_PORT = 54000;

    /**星历单包最大条数*/
    public static final int MAX_EPHES_POINTS_PER_PACKET = 20;
}