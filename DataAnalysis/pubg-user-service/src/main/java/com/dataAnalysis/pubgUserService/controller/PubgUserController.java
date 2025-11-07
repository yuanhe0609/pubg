package com.dataAnalysis.pubgUserService.controller;

import com.dataAnalysis.pubgCommonService.entity.Result;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pubg/user")
@Validated
public class PubgUserController {
    @Resource
    private PubgUserService userService;
    @PostMapping("/login")
    public Result<?> login(@RequestBody @Validated PubgUserEntity user) {
        return Result.ok(userService.login(user));
    }
    @GetMapping("/getName")
    public Result<?> getName() {
        return Result.ok("yes");
    }
}
