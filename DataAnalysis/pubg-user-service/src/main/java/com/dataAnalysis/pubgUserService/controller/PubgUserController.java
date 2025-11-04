package com.dataAnalysis.pubgUserService.controller;

import com.dataAnalysis.pubgEntityRegister.entity.Result;
import com.dataAnalysis.pubgUserService.entity.PubgUserEntity;
import com.dataAnalysis.pubgUserService.service.PubgUserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
