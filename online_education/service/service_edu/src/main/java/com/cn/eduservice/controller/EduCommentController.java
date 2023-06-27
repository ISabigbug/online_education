package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduComment;
import com.cn.eduservice.domain.vo.CommentQuery;
import com.cn.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 评论(EduComment)表控制层
 *
 * @author makejava
 * @since 2023-04-23 17:25:08
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    /**
     * 服务对象
     */
    @Autowired
    private EduCommentService eduCommentService;

    //1.分页显示所有评论信息
    @PostMapping ("pageComment/{page}/{limit}")
    public Result pageCourse(@PathVariable long page, @PathVariable long limit,
                                  @RequestBody(required = false) CommentQuery commentQuery) {
        Page<EduComment> pageComment = new Page<>(page, limit);
        Map<String, Object> commentList = eduCommentService.pageCourse(pageComment,commentQuery);
        return Result.success().data(commentList);
    }

    //根据评论ID删除评论信息
    @DeleteMapping("removeComment/{cid}")
    public Result removeComment(@PathVariable String cid) {
        eduCommentService.removeById(cid);
        return Result.success();
    }

    //批量删除评论信息
    @DeleteMapping("removeCommentList")
    public Result removeCommentList(@RequestBody List<String> cidList) {
        eduCommentService.removeBatchByIds(cidList);
        return Result.success();
    }
}

