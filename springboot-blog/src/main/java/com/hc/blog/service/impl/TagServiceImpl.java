package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.ArticleTag;
import com.hc.blog.entity.Tag;
import com.hc.blog.entity.vo.TagPageVo;
import com.hc.blog.mapper.ArticleTagMapper;
import com.hc.blog.mapper.TagMapper;
import com.hc.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Cacheable(value = "pageListTag", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#tagName")
    @Override
    public R pageList(Integer pageNum, Integer pageSize, String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tagName)) {
            queryWrapper.like(Tag::getTagName, tagName);
        }
        Page<Tag> page = new Page<>(pageNum, pageSize);
        Page<Tag> tagPage = tagMapper.selectPage(page, queryWrapper);
        List<TagPageVo> tagPageVoList = new ArrayList<>();
        for (Tag tag : tagPage.getRecords()) {
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getTagId, tag.getId());
            Long count = articleTagMapper.selectCount(wrapper);
            TagPageVo tagPageVo = new TagPageVo();
            tagPageVo.setId(tag.getId());
            tagPageVo.setTagName(tag.getTagName());
            tagPageVo.setArticleCount(count);
            tagPageVo.setCreateTime(tag.getCreateTime());
            tagPageVoList.add(tagPageVo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", tagPage.getTotal());
        map.put("tagList", tagPageVoList);
        return R.okResult(map);
    }

    /**
     * 查询所有便签名称
     *
     * @return
     */
    @Cacheable(value = "list.tag", key = "#root.methodName")
    @Override
    public R selectTagName() {
        List<Tag> tags = tagMapper.selectList(null);
        List<String> tagName = tags.stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
        return R.okResult(tagName);
    }
}
