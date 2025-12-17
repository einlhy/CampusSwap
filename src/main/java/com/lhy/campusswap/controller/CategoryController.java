package com.lhy.campusswap.controller;

import com.lhy.campusswap.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @GetMapping("/list")
    public ResponseResult listCategories() {
        // TODO 获取所有启用的分类列表
        return null;
    }

    @GetMapping("/search/{id}")
    public ResponseResult getCategoryById() {
        // TODO 通过分类ID获取分类信息
        return null;
    }

    @PostMapping("/create")
    public ResponseResult createCategory() {
        // TODO 创建新分类（管理员）
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseResult updateCategory() {
        // TODO 更新分类信息（管理员）
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteCategory() {
        // TODO 删除分类（管理员）
        return null;
    }

    @PutMapping("/status/{id}")
    public ResponseResult changeCategoryStatus() {
        // TODO 启用或禁用分类（管理员）
        return null;
    }
}
