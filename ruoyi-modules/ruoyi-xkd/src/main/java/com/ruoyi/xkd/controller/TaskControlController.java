package com.ruoyi.xkd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.xkd.task.AntennaQueryTask;

@RestController
@RequestMapping("/api/xkd/task")
public class TaskControlController {

    private static final Logger log = LoggerFactory.getLogger(TaskControlController.class);

    private final AntennaQueryTask antennaQueryTask;

    public TaskControlController(AntennaQueryTask antennaQueryTask) {
        this.antennaQueryTask = antennaQueryTask;
    }

    @GetMapping("/start")
    public ResponseEntity<?> startTask() {
        antennaQueryTask.start();
        log.info("定时任务已启动");
        return ResponseEntity.ok("定时任务已启动");
    }

    @GetMapping("/stop")
    public ResponseEntity<?> stopTask() {
        antennaQueryTask.stop();
        log.info("定时任务已停止");
        return ResponseEntity.ok("定时任务已停止");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(antennaQueryTask.isRunning());
    }
}