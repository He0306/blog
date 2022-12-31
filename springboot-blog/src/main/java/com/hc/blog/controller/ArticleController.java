package com.hc.blog.controller;


import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.dto.AddArticleDto;
import com.hc.blog.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Api(tags = "文章模块")
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    IArticleService articleService;

    /**
     * 分页查询全部文章
     *
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @ApiOperation(value = "分页查询全部文章")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String title,
                      @RequestParam String summary) {
        return articleService.pageList(pageNum, pageSize, title, summary);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询")
    @GetMapping("/{id}")
    public R getByArticleId(@PathVariable("id") String id) {
        return articleService.getByArticleId(id);
    }

    /**
     * 新增
     *
     * @param addArticleDto
     * @return
     */
    @OptLog(optType = SAVE)
    @ApiOperation(value = "新增")
    @PostMapping
    public R addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    /**
     * 保存到草稿箱
     *
     * @param addArticleDto
     * @return
     */
    @OptLog(optType = SAVE)
    @ApiOperation(value = "保存到草稿箱")
    @PostMapping("/drafts")
    public R drafts(@RequestBody AddArticleDto addArticleDto) {
        return articleService.drafts(addArticleDto);
    }

    /**
     * 修改状态
     *
     * @param article
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation("修改状态")
    @PostMapping("/updateStatus")
    public R updateStatus(@RequestBody Article article) {
        return articleService.updateStatus(article);
    }

    /**
     * 修改
     *
     * @param addArticleDto
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "修改")
    @PutMapping
    public R updateArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除文章")
    @OptLog(optType = UPDATE)
    @DeleteMapping("/{id}")
    public R removeArticle(@PathVariable("id") String id) {
        return articleService.removeArticle(id);
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID批量删除文章")
    @PostMapping("/delete/batch")
    public R batchRemoveArticle(@RequestBody List<String> ids) {
        return articleService.batchRemoveArticle(ids);
    }

}

