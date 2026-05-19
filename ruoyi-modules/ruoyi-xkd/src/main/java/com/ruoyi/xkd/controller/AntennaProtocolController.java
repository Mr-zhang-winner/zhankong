package com.ruoyi.xkd.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.service.AntennaCommandBuildService;
import com.ruoyi.xkd.protocol.service.AntennaProtocolSendService;

@RestController
public class AntennaProtocolController extends BaseController
{
    private final AntennaCommandBuildService commandBuildService;
    private final AntennaProtocolSendService sendService;

    public AntennaProtocolController(AntennaCommandBuildService commandBuildService,
                                     AntennaProtocolSendService sendService)
    {
        this.commandBuildService = commandBuildService;
        this.sendService = sendService;
    }

    @PostMapping("/antenna/set/bucDecay")
    public AjaxResult setBucDecay(@RequestBody Map<String, Object> body)
    {
        String deviceCode = String.valueOf(body.get("deviceCode"));
        String ip = String.valueOf(body.get("ip"));
        Integer port = Integer.valueOf(String.valueOf(body.get("port")));
        Double decay = Double.valueOf(String.valueOf(body.get("decay")));

        byte[] payload = commandBuildService.buildSetBucDecayPayload(decay);

        sendService.send(deviceCode, ip, port, AntennaProtocolConstants.CMD_SET, payload);

        return success("BUC衰减设置命令已发送");
    }

    @PostMapping("/antenna/set/bdcDecay")
    public AjaxResult setBdcDecay(@RequestBody Map<String, Object> body)
    {
        String deviceCode = String.valueOf(body.get("deviceCode"));
        String ip = String.valueOf(body.get("ip"));
        Integer port = Integer.valueOf(String.valueOf(body.get("port")));
        Double decay = Double.valueOf(String.valueOf(body.get("decay")));

        byte[] payload = commandBuildService.buildSetBdcDecayPayload(decay);

        sendService.send(deviceCode, ip, port, AntennaProtocolConstants.CMD_SET, payload);

        return success("BDC衰减设置命令已发送");
    }

    @PostMapping("/antenna/query/status")
    public AjaxResult queryStatus(@RequestBody Map<String, Object> body)
    {
        String deviceCode = String.valueOf(body.get("deviceCode"));
        String ip = String.valueOf(body.get("ip"));
        Integer port = Integer.valueOf(String.valueOf(body.get("port")));

        byte[] payload = commandBuildService.buildQueryPayload();

        sendService.send(deviceCode, ip, port, AntennaProtocolConstants.CMD_QUERY, payload);

        return success("全查询命令已发送");
    }

    @PostMapping("/antenna/query/factory")
    public AjaxResult queryFactory(@RequestBody Map<String, Object> body)
    {
        String deviceCode = String.valueOf(body.get("deviceCode"));
        String ip = String.valueOf(body.get("ip"));
        Integer port = Integer.valueOf(String.valueOf(body.get("port")));

        byte[] payload = commandBuildService.buildFactoryQueryPayload();

        sendService.send(deviceCode, ip, port, AntennaProtocolConstants.CMD_FAC_QUERY, payload);

        return success("出厂信息查询命令已发送");
    }

    @PostMapping("/antenna/search/broadcast")
    public AjaxResult broadcastSearch()
    {
        byte[] payload = commandBuildService.buildBroadcastSearchPayload();

        sendService.broadcast(AntennaProtocolConstants.CMD_BRD_SEARCH, payload);

        return success("广播搜索命令已发送");
    }
}
