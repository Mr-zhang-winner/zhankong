package com.ruoyi.xkd.protocol.codec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ruoyi.xkd.protocol.model.AntennaParam;
import com.ruoyi.xkd.protocol.model.BitSw;
import com.ruoyi.xkd.protocol.model.EphesParam;
import com.ruoyi.xkd.protocol.model.FactoryParam;
import com.ruoyi.xkd.protocol.model.QueryParam;
import com.ruoyi.xkd.protocol.model.SetParam;
import com.ruoyi.xkd.protocol.model.param.AntAlarmParam;
import com.ruoyi.xkd.protocol.model.param.AntAttitudeParam;
import com.ruoyi.xkd.protocol.model.param.AntBdcDecayParam;
import com.ruoyi.xkd.protocol.model.param.AntBucDecayParam;
import com.ruoyi.xkd.protocol.model.param.AntCapGainParam;
import com.ruoyi.xkd.protocol.model.param.AntCapParam;
import com.ruoyi.xkd.protocol.model.param.AntMfRxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntMfTxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntModeParam;
import com.ruoyi.xkd.protocol.model.param.AntPsrpParam;
import com.ruoyi.xkd.protocol.model.param.AntRxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntRxPolParam;
import com.ruoyi.xkd.protocol.model.param.AntSatIdParam;
import com.ruoyi.xkd.protocol.model.param.AntStatusParam;
import com.ruoyi.xkd.protocol.model.param.AntSwStateParam;
import com.ruoyi.xkd.protocol.model.param.AntTempParam;
import com.ruoyi.xkd.protocol.model.param.AntTxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntTxPolParam;
import com.ruoyi.xkd.protocol.model.param.ChangeSatParam;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;

@Component
public class AntennaParamCodec
{
    public byte[] encodeParams(List<AntennaParam> params)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for (AntennaParam param : params)
        {
            if (param.getValue() == null)
            {
                throw new IllegalArgumentException("参数 value 不能为空，key=" + param.getKey());
            }

            if (param.getLen() != param.getValue().length)
            {
                throw new IllegalArgumentException("参数 len 与 value.length 不一致，key=" + param.getKey());
            }

            try {
                out.write(param.getKey() & 0xFF);
                out.write(param.getLen() & 0xFF);
                out.write(param.getValue(), 0, param.getValue().length);
            } catch (Exception e) {
                throw new RuntimeException("编码参数失败", e);
            }
        }

        return out.toByteArray();
    }

    public List<AntennaParam> decodeParams(byte[] payload)
    {
        List<AntennaParam> params = new ArrayList<>();

        if (payload == null || payload.length == 0)
        {
            return params;
        }

        int index = 0;

        while (index + 2 <= payload.length)
        {
            int key = payload[index] & 0xFF;
            int len = payload[index + 1] & 0xFF;

            if (index + 2 + len > payload.length)
            {
                throw new IllegalArgumentException("参数长度越界，key=" + key + ", len=" + len);
            }

            byte[] value = new byte[len];
            System.arraycopy(payload, index + 2, value, 0, len);

            params.add(new AntennaParam(key, len, value));

            index += 2 + len;
        }

        return params;
    }

    // ==================== 参数类型结构体编码方法 ====================

    public byte[] encodeAntModeParam(AntModeParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntMode().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntTxFreqParam(AntTxFreqParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntTxFreq().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntRxFreqParam(AntRxFreqParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntRxFreq().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntMfTxFreqParam(AntMfTxFreqParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntMfTxFreq().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntMfRxFreqParam(AntMfRxFreqParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntMfRxFreq().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntTxPolParam(AntTxPolParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntTxPol() & 0xFF);
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntRxPolParam(AntRxPolParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntRxPol() & 0xFF);
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntBucDecayParam(AntBucDecayParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntBucDecay().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntBdcDecayParam(AntBdcDecayParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntBdcDecay().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntSatIdParam(AntSatIdParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getSatId().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntTempParam(AntTempParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntTemp().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntAlarmParam(AntAlarmParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntAlarm().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntAttitudeParam(AntAttitudeParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntAzimuth().getBytes());
            out.write(param.getAntPitch().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntPsrpParam(AntPsrpParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getAntPsrp().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntSwStateParam(AntSwStateParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getSwState().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeBitSw(BitSw bitSw) {
        return bitSw.getBytes();
    }

    public BitSw decodeBitSw(byte[] data, int offset) {
        BitSw bitSw = new BitSw();
        if (data != null && offset < data.length) {
            bitSw.setValue(data[offset] & 0xFF);
        }
        return bitSw;
    }

    public byte[] encodeAntCapParam(AntCapParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getEirp() & 0xFF);
            out.write(param.getMinEirp() & 0xFF);
            out.write(param.getAntGt().getBytes());
            out.write(param.getMinGt().getBytes());
            out.write(param.getUePitch() & 0xFF);
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeAntCapGainParam(AntCapGainParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getGain() & 0xFF);
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    public byte[] encodeChangeSatParam(ChangeSatParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getKey() & 0xFF);
            out.write(param.getLen() & 0xFF);
            out.write(param.getSatId().getBytes());
            out.write(param.getIsValid() & 0xFF);
            out.write(param.getStartChange() & 0xFF);
            out.write(param.getGpsWeek().getBytes());
            out.write(param.getGpsSecond().getBytes());
            out.write(param.getGpsMsecond().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    // ==================== 参数类型结构体解码方法 ====================

    public AntModeParam decodeAntModeParam(byte[] data, int offset) {
        AntModeParam param = new AntModeParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[1];
        System.arraycopy(data, offset + 2, value, 0, 1);
        param.getAntMode().setBytes(value);
        return param;
    }

    public AntTxFreqParam decodeAntTxFreqParam(byte[] data, int offset) {
        AntTxFreqParam param = new AntTxFreqParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[4];
        System.arraycopy(data, offset + 2, value, 0, 4);
        param.getAntTxFreq().setBytes(value);
        return param;
    }

    public AntRxFreqParam decodeAntRxFreqParam(byte[] data, int offset) {
        AntRxFreqParam param = new AntRxFreqParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[4];
        System.arraycopy(data, offset + 2, value, 0, 4);
        param.getAntRxFreq().setBytes(value);
        return param;
    }

    public AntTxPolParam decodeAntTxPolParam(byte[] data, int offset) {
        AntTxPolParam param = new AntTxPolParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        param.setAntTxPol(data[offset + 2] & 0xFF);
        return param;
    }

    public AntRxPolParam decodeAntRxPolParam(byte[] data, int offset) {
        AntRxPolParam param = new AntRxPolParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        param.setAntRxPol(data[offset + 2] & 0xFF);
        return param;
    }

    public AntBucDecayParam decodeAntBucDecayParam(byte[] data, int offset) {
        AntBucDecayParam param = new AntBucDecayParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getAntBucDecay().setBytes(value);
        return param;
    }

    public AntBdcDecayParam decodeAntBdcDecayParam(byte[] data, int offset) {
        AntBdcDecayParam param = new AntBdcDecayParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getAntBdcDecay().setBytes(value);
        return param;
    }

    public AntSatIdParam decodeAntSatIdParam(byte[] data, int offset) {
        AntSatIdParam param = new AntSatIdParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getSatId().setBytes(value);
        return param;
    }

    public AntTempParam decodeAntTempParam(byte[] data, int offset) {
        AntTempParam param = new AntTempParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getAntTemp().setBytes(value);
        return param;
    }

    public AntAlarmParam decodeAntAlarmParam(byte[] data, int offset) {
        AntAlarmParam param = new AntAlarmParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getAntAlarm().setBytes(value);
        return param;
    }

    public AntStatusParam decodeAntStatusParam(byte[] data, int offset) {
        AntStatusParam param = new AntStatusParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        param.setAntStatus(data[offset + 2] & 0xFF);
        return param;
    }

    public AntAttitudeParam decodeAntAttitudeParam(byte[] data, int offset) {
        AntAttitudeParam param = new AntAttitudeParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] az = new byte[2];
        byte[] el = new byte[2];
        System.arraycopy(data, offset + 2, az, 0, 2);
        System.arraycopy(data, offset + 4, el, 0, 2);
        param.getAntAzimuth().setBytes(az);
        param.getAntPitch().setBytes(el);
        return param;
    }

    public AntPsrpParam decodeAntPsrpParam(byte[] data, int offset) {
        AntPsrpParam param = new AntPsrpParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[2];
        System.arraycopy(data, offset + 2, value, 0, 2);
        param.getAntPsrp().setBytes(value);
        return param;
    }

    public AntSwStateParam decodeAntSwStateParam(byte[] data, int offset) {
        AntSwStateParam param = new AntSwStateParam();
        param.setKey(data[offset] & 0xFF);
        param.setLen(data[offset + 1] & 0xFF);
        byte[] value = new byte[1];
        System.arraycopy(data, offset + 2, value, 0, 1);
        param.getSwState().setBytes(value);
        return param;
    }

    // ==================== 复合结构体编解码方法 ====================

    public QueryParam decodeQueryParam(byte[] payload) {
        QueryParam query = new QueryParam();
        
        if (payload == null || payload.length == 0) {
            return query;
        }

        int index = 0;
        while (index + 2 <= payload.length) {
            int key = payload[index] & 0xFF;
            int len = payload[index + 1] & 0xFF;

            if (index + 2 + len > payload.length) {
                break;
            }

            switch (key) {
                case AntennaProtocolConstants.KEY_SEND_FREQ:
                    query.setAntTxFreqT(decodeAntTxFreqParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_RECV_FREQ:
                    query.setAntRxFreqT(decodeAntRxFreqParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_TX_POL:
                    query.setAntTxPolT(decodeAntTxPolParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_RX_POL:
                    query.setAntRxPolT(decodeAntRxPolParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_BUC_DECAY:
                    query.setAntBucDecayT(decodeAntBucDecayParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_BDC_DECAY:
                    query.setAntBdcDecayT(decodeAntBdcDecayParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_SAT_NO:
                    query.setAntSatIdT(decodeAntSatIdParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_TEMP:
                    query.setAntTempT(decodeAntTempParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_STATUS:
                    query.setAntStatusT(decodeAntStatusParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_ANGLE:
                    query.setAntAttitudeT(decodeAntAttitudeParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_RSRP:
                    query.setAntPsrpT(decodeAntPsrpParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_SWITCH:
                    // 天线开关状态
                    break;
                case AntennaProtocolConstants.KEY_ANT_PAR_STATUS:
                    // 参数状态
                    break;
                case AntennaProtocolConstants.KEY_LOCK_STATUS:
                    // 锁定状态
                    break;
                case AntennaProtocolConstants.KEY_ACU_STATUS:
                    // ACU状态
                    break;
                default:
                    break;
            }

            index += 2 + len;
        }

        return query;
    }

    public SetParam decodeSetParam(byte[] payload) {
        SetParam set = new SetParam();
        
        if (payload == null || payload.length == 0) {
            return set;
        }

        int index = 0;
        while (index + 2 <= payload.length) {
            int key = payload[index] & 0xFF;
            int len = payload[index + 1] & 0xFF;

            if (index + 2 + len > payload.length) {
                break;
            }

            switch (key) {
                case AntennaProtocolConstants.KEY_SAT_NO:
                    // 使用卫星ID
                    break;
                case AntennaProtocolConstants.KEY_SEND_FREQ:
                    set.setAntTxFreqT(decodeAntTxFreqParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_RECV_FREQ:
                    set.setAntRxFreqT(decodeAntRxFreqParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_TX_POL:
                    set.setAntTxPolT(decodeAntTxPolParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_RX_POL:
                    set.setAntRxPolT(decodeAntRxPolParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_BUC_DECAY:
                    set.setAntBucDecayT(decodeAntBucDecayParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_BDC_DECAY:
                    set.setAntBdcDecayT(decodeAntBdcDecayParam(payload, index));
                    break;
                case AntennaProtocolConstants.KEY_ANT_SWITCH:
                    set.setAntSwStateT(decodeAntSwStateParam(payload, index));
                    break;
                default:
                    break;
            }

            index += 2 + len;
        }

        return set;
    }

    public byte[] encodeQueryParam(QueryParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        return out.toByteArray();
    }

    public byte[] encodeSetParam(SetParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (param.getAntTxFreqT().getKey() != 0) {
                out.write(encodeAntTxFreqParam(param.getAntTxFreqT()));
            }
            if (param.getAntRxFreqT().getKey() != 0) {
                out.write(encodeAntRxFreqParam(param.getAntRxFreqT()));
            }
            if (param.getAntTxPolT().getKey() != 0) {
                out.write(encodeAntTxPolParam(param.getAntTxPolT()));
            }
            if (param.getAntRxPolT().getKey() != 0) {
                out.write(encodeAntRxPolParam(param.getAntRxPolT()));
            }
            if (param.getAntBucDecayT().getKey() != 0) {
                out.write(encodeAntBucDecayParam(param.getAntBucDecayT()));
            }
            if (param.getAntBdcDecayT().getKey() != 0) {
                out.write(encodeAntBdcDecayParam(param.getAntBdcDecayT()));
            }
            if (param.getAntSwStateT().getKey() != 0) {
                out.write(encodeAntSwStateParam(param.getAntSwStateT()));
            }
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    // ==================== FactoryParam 编解码 ====================

    public FactoryParam decodeFactoryParam(byte[] payload) {
        FactoryParam factory = new FactoryParam();
        
        if (payload == null || payload.length < 2) {
            return factory;
        }

        int index = 0;
        while (index + 2 <= payload.length) {
            int key = payload[index] & 0xFF;
            int len = payload[index + 1] & 0xFF;

            if (index + 2 + len > payload.length) {
                break;
            }

            byte[] value = new byte[len];
            System.arraycopy(payload, index + 2, value, 0, len);

            switch (key) {
                case 0x6E: // 设备名称
                    factory.setStrDeviceName(new String(value, StandardCharsets.US_ASCII).trim());
                    break;
                case 0x6F: // 厂商信息
                    factory.setStrDeviceOem(new String(value, StandardCharsets.US_ASCII).trim());
                    break;
                case 0x70: // 序列号
                    factory.setStrDeviceSn(new String(value, StandardCharsets.US_ASCII).trim());
                    break;
                case 0x71: // 生产日期
                    factory.setStrDeviceDate(new String(value, StandardCharsets.US_ASCII).trim());
                    break;
                case 0x72: // 软件版本
                    factory.setStrSoftVersion(new String(value, StandardCharsets.US_ASCII).trim());
                    break;
                default:
                    break;
            }

            index += 2 + len;
        }

        factory.setDeviceInfoValid(1);
        return factory;
    }

    public byte[] encodeFactoryParam(FactoryParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] name = new byte[16];
            byte[] nameBytes = param.getStrDeviceName() != null ? param.getStrDeviceName().getBytes(StandardCharsets.US_ASCII) : new byte[0];
            System.arraycopy(nameBytes, 0, name, 0, Math.min(nameBytes.length, 16));
            out.write(name);

            byte[] oem = new byte[24];
            byte[] oemBytes = param.getStrDeviceOem() != null ? param.getStrDeviceOem().getBytes(StandardCharsets.US_ASCII) : new byte[0];
            System.arraycopy(oemBytes, 0, oem, 0, Math.min(oemBytes.length, 24));
            out.write(oem);

            byte[] sn = new byte[16];
            byte[] snBytes = param.getStrDeviceSn() != null ? param.getStrDeviceSn().getBytes(StandardCharsets.US_ASCII) : new byte[0];
            System.arraycopy(snBytes, 0, sn, 0, Math.min(snBytes.length, 16));
            out.write(sn);

            byte[] date = new byte[16];
            byte[] dateBytes = param.getStrDeviceDate() != null ? param.getStrDeviceDate().getBytes(StandardCharsets.US_ASCII) : new byte[0];
            System.arraycopy(dateBytes, 0, date, 0, Math.min(dateBytes.length, 16));
            out.write(date);

            byte[] version = new byte[16];
            byte[] versionBytes = param.getStrSoftVersion() != null ? param.getStrSoftVersion().getBytes(StandardCharsets.US_ASCII) : new byte[0];
            System.arraycopy(versionBytes, 0, version, 0, Math.min(versionBytes.length, 16));
            out.write(version);

            out.write(param.getDeviceInfoValid() & 0xFF);
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }

    // ==================== EphesParam 编解码 ====================

    public EphesParam decodeEphesParam(byte[] payload) {
        EphesParam ephes = new EphesParam();
        
        if (payload == null || payload.length < 10) {
            return ephes;
        }

        int offset = 0;
        
        byte[] satId = new byte[2];
        System.arraycopy(payload, offset, satId, 0, 2);
        ephes.getSatId().setBytes(satId);
        offset += 2;

        byte[] dataNum = new byte[2];
        System.arraycopy(payload, offset, dataNum, 0, 2);
        ephes.getDataNum().setBytes(dataNum);
        offset += 2;

        byte[] curTotal = new byte[1];
        System.arraycopy(payload, offset, curTotal, 0, 1);
        ephes.getCurTotalNum().setBytes(curTotal);
        offset += 1;

        int points = ephes.getDataNum().getA();
        for (int i = 0; i < Math.min(points, 20) && offset + 4 <= payload.length; i++) {
            byte[] week = new byte[4];
            System.arraycopy(payload, offset, week, 0, 4);
            ephes.getGpsWeek()[i].setBytes(week);
            offset += 4;
        }

        for (int i = 0; i < Math.min(points, 20) && offset + 4 <= payload.length; i++) {
            byte[] second = new byte[4];
            System.arraycopy(payload, offset, second, 0, 4);
            ephes.getGpsSecond()[i].setBytes(second);
            offset += 4;
        }

        for (int i = 0; i < Math.min(points, 20) && offset + 8 <= payload.length; i++) {
            byte[] x = new byte[8];
            System.arraycopy(payload, offset, x, 0, 8);
            ephes.getWgs84X()[i].setBytes(x);
            offset += 8;
        }

        for (int i = 0; i < Math.min(points, 20) && offset + 8 <= payload.length; i++) {
            byte[] y = new byte[8];
            System.arraycopy(payload, offset, y, 0, 8);
            ephes.getWgs84Y()[i].setBytes(y);
            offset += 8;
        }

        for (int i = 0; i < Math.min(points, 20) && offset + 8 <= payload.length; i++) {
            byte[] z = new byte[8];
            System.arraycopy(payload, offset, z, 0, 8);
            ephes.getWgs84Z()[i].setBytes(z);
            offset += 8;
        }

        if (offset + 8 <= payload.length) {
            byte[] vx = new byte[8];
            System.arraycopy(payload, offset, vx, 0, 8);
            ephes.getWgs84Vx().setBytes(vx);
            offset += 8;
        }

        if (offset + 8 <= payload.length) {
            byte[] vy = new byte[8];
            System.arraycopy(payload, offset, vy, 0, 8);
            ephes.getWgs84Vy().setBytes(vy);
            offset += 8;
        }

        if (offset + 8 <= payload.length) {
            byte[] vz = new byte[8];
            System.arraycopy(payload, offset, vz, 0, 8);
            ephes.getWgs84Vz().setBytes(vz);
            offset += 8;
        }

        if (offset + 4 <= payload.length) {
            byte[] seq = new byte[4];
            System.arraycopy(payload, offset, seq, 0, 4);
            ephes.getDataSeq().setBytes(seq);
            offset += 4;
        }

        if (offset < payload.length) {
            ephes.setGetWeekOffsetOk(payload[offset] & 0xFF);
            offset++;
        }

        if (offset + 2 <= payload.length) {
            byte[] weekOffset = new byte[2];
            System.arraycopy(payload, offset, weekOffset, 0, 2);
            ephes.setWeekOffsetT(ByteCodec.readU16(weekOffset, 0));
        }

        return ephes;
    }

    public byte[] encodeEphesParam(EphesParam param) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(param.getSatId().getBytes());
            out.write(param.getDataNum().getBytes());
            out.write(param.getCurTotalNum().getBytes());

            int points = param.getDataNum().getA();
            for (int i = 0; i < Math.min(points, 20); i++) {
                out.write(param.getGpsWeek()[i].getBytes());
            }
            for (int i = 0; i < Math.min(points, 20); i++) {
                out.write(param.getGpsSecond()[i].getBytes());
            }
            for (int i = 0; i < Math.min(points, 20); i++) {
                out.write(param.getWgs84X()[i].getBytes());
            }
            for (int i = 0; i < Math.min(points, 20); i++) {
                out.write(param.getWgs84Y()[i].getBytes());
            }
            for (int i = 0; i < Math.min(points, 20); i++) {
                out.write(param.getWgs84Z()[i].getBytes());
            }

            out.write(param.getWgs84Vx().getBytes());
            out.write(param.getWgs84Vy().getBytes());
            out.write(param.getWgs84Vz().getBytes());
            out.write(param.getDataSeq().getBytes());
            out.write(param.getGetWeekOffsetOk() & 0xFF);
            out.write(ByteCodec.u16(param.getWeekOffsetT()));
        } catch (Exception e) {
            throw new RuntimeException("编码失败", e);
        }
        return out.toByteArray();
    }
}
