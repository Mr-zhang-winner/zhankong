package com.ruoyi.xkd.protocol.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.codec.ByteCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaParam;
import com.ruoyi.xkd.protocol.model.param.AntBdcDecayParam;
import com.ruoyi.xkd.protocol.model.param.AntBucDecayParam;
import com.ruoyi.xkd.protocol.model.param.AntRxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntRxPolParam;
import com.ruoyi.xkd.protocol.model.param.AntSatIdParam;
import com.ruoyi.xkd.protocol.model.param.AntSwStateParam;
import com.ruoyi.xkd.protocol.model.param.AntTxFreqParam;
import com.ruoyi.xkd.protocol.model.param.AntTxPolParam;

@Service
public class AntennaCommandBuildService
{
    private final AntennaParamCodec paramCodec;

    public AntennaCommandBuildService(AntennaParamCodec paramCodec)
    {
        this.paramCodec = paramCodec;
    }

    // ==================== 使用结构体构建命令 ====================

    public byte[] buildSetTxFreqPayload(long freqHz)
    {
        AntTxFreqParam param = new AntTxFreqParam();
        param.setKey(AntennaProtocolConstants.KEY_SEND_FREQ);
        param.getAntTxFreq().setA(freqHz);
        return paramCodec.encodeAntTxFreqParam(param);
    }

    public byte[] buildSetRxFreqPayload(long freqHz)
    {
        AntRxFreqParam param = new AntRxFreqParam();
        param.setKey(AntennaProtocolConstants.KEY_RECV_FREQ);
        param.getAntRxFreq().setA(freqHz);
        return paramCodec.encodeAntRxFreqParam(param);
    }

    public byte[] buildSetBucDecayPayload(double decay)
    {
        int value = (int) Math.round(decay * 100);
        AntBucDecayParam param = new AntBucDecayParam();
        param.setKey(AntennaProtocolConstants.KEY_BUC_DECAY);
        param.getAntBucDecay().setA(value);
        return paramCodec.encodeAntBucDecayParam(param);
    }

    public byte[] buildSetBdcDecayPayload(double decay)
    {
        int value = (int) Math.round(decay * 100);
        AntBdcDecayParam param = new AntBdcDecayParam();
        param.setKey(AntennaProtocolConstants.KEY_BDC_DECAY);
        param.getAntBdcDecay().setA(value);
        return paramCodec.encodeAntBdcDecayParam(param);
    }

    public byte[] buildSetTxPolPayload(int txPol)
    {
        AntTxPolParam param = new AntTxPolParam();
        param.setKey(AntennaProtocolConstants.KEY_ANT_TX_POL);
        param.setAntTxPol(txPol);
        return paramCodec.encodeAntTxPolParam(param);
    }

    public byte[] buildSetRxPolPayload(int rxPol)
    {
        AntRxPolParam param = new AntRxPolParam();
        param.setKey(AntennaProtocolConstants.KEY_ANT_RX_POL);
        param.setAntRxPol(rxPol);
        return paramCodec.encodeAntRxPolParam(param);
    }

    public byte[] buildSetSatNoPayload(int satNo)
    {
        AntSatIdParam param = new AntSatIdParam();
        param.setKey(AntennaProtocolConstants.KEY_SAT_NO);
        param.getSatId().setA(satNo);
        return paramCodec.encodeAntSatIdParam(param);
    }

    public byte[] buildAntSwitchPayload(int swTx, int swRx, int swFollow)
    {
        int value = 0;
        value |= (swTx & 0x03);
        value |= (swRx & 0x03) << 2;
        value |= (swFollow & 0x03) << 4;

        AntSwStateParam param = new AntSwStateParam();
        param.setKey(AntennaProtocolConstants.KEY_ANT_SWITCH);
        param.getSwState().setA(value);
        return paramCodec.encodeAntSwStateParam(param);
    }

    // ==================== 原有方法保持兼容 ====================

    public byte[] buildAnglePayload(double azimuth, double elevation)
    {
        int az = (int) Math.round(azimuth * 100);
        int el = (int) Math.round(elevation * 100);

        byte[] value = new byte[4];
        value[0] = (byte) ((az >> 8) & 0xFF);
        value[1] = (byte) (az & 0xFF);
        value[2] = (byte) ((el >> 8) & 0xFF);
        value[3] = (byte) (el & 0xFF);

        return singleParam(AntennaProtocolConstants.KEY_ANT_ANGLE, value);
    }

    public byte[] buildQueryPayload()
    {
        return new byte[0];
    }

    public byte[] buildFactoryQueryPayload()
    {
        return new byte[0];
    }

    public byte[] buildBroadcastSearchPayload()
    {
        return new byte[0];
    }

    private byte[] singleParam(int key, byte[] value)
    {
        List<AntennaParam> params = new ArrayList<>();
        params.add(new AntennaParam(key, value.length, value));
        return paramCodec.encodeParams(params);
    }
}
