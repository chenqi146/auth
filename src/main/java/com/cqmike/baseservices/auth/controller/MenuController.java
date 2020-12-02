package com.cqmike.baseservices.auth.controller;

import com.cqmike.base.controller.BaseController;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @program: baseServices
 * @description: 菜单
 * @author: chen qi
 * @create: 2020-11-28 12:15
 **/

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;

    @PostMapping("/create")
    public ReturnForm<Menu> create(@RequestBody Menu menu) {
        return ReturnForm.ok(menuService.create(menu));
    }

    @PutMapping("/update")
    public ReturnForm<Menu> update(@RequestBody Menu menu) {
        Objects.requireNonNull(menu.getId(), "更新id不能为空");
        return ReturnForm.ok(menuService.update(menu));
    }
}
