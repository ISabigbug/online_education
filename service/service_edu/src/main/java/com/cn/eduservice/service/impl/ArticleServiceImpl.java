package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.frontvo.ECourse;
import com.cn.eduservice.domain.vo.ArticleQuery;
import com.cn.eduservice.mapper.ArticleMapper;
import com.cn.eduservice.service.ArticleService;
import com.cn.servicebase.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * @Project: online_education
 * @Package: com.cn.eduservice.service.impl
 * @Description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RedisCache redisCache;
    @Override
    public Map<String, Object> pageArticleFront(Page<Article> pageArticle, ArticleQuery articleQuery) {
        //根据分页条件查询
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(articleQuery.getTitle()), Article::getTitle, articleQuery.getTitle())
                .like(StringUtils.isNotBlank(articleQuery.getCategoryName()), Article::getCategoryName, articleQuery.getCategoryName())
                .orderByDesc(Article::getIsTop);
        baseMapper.selectPage(pageArticle, lambdaQueryWrapper);
        List<Article> articleList = pageArticle.getRecords();
        for (Article article : articleList) {
            //从redis获得viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        //将查询结果封装到map中
        long current = pageArticle.getCurrent();
        long pages = pageArticle.getPages();
        long size = pageArticle.getSize();
        long total = pageArticle.getTotal();
        boolean hasNext = pageArticle.hasNext();
        boolean hasPrevious = pageArticle.hasPrevious();
        Map<String, Object> map = new HashMap();
        map.put("records", articleList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
//    @Autowired
//    RedisCache redisCache;
//    @Autowired
//    private ArticleTagService articleTagService;
//    @Autowired
//    private CommentService commentService;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//
//    @Override
//    public Result articleList(Integer pageNum, Integer pageSize) {
//        //查询条件
//        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        //状态是正式发布的
//        lambdaQueryWrapper.eq(Article::getStatus,"1");
//        //istop进行降序
//        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
//
//        //分页查询
//        Page<Article> page=new Page<>(pageNum,pageSize);
//        page(page,lambdaQueryWrapper);
//        //查询categoryName
//        List<Article> articles=page.getRecords();
//        articles.stream()
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());
//        //封装查询结果                                                   数据地址一样
//        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
//
//
//        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
//        return ResponseResult.okResult(pageVo);
//    }
//
//
//    private static final ExecutorService CACHE_REBUILD_EXECUTOR= Executors.newFixedThreadPool(10);
//
//    /*
//     *@Description:  逻辑过期日期  优化并发查询
//     *@Params:
//     *@Return:
//     *@Author:krrz
//     *@Date:2023/4/26
//     */
//    public ArticleDetailVo queryWithLogicExpire(Long id){
//        String key= RedisConstants.article_id +id;
//        //从redis查询商户缓存
//        String json = stringRedisTemplate.opsForValue().get(key);
//        //判断是否存在
//        if (StrUtil.isBlank(json)) {
//            //存在 直接返回
//            return null;
//        }
//        //命中  需要先把json反序列化为对象
//        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
//        JSONObject data = (JSONObject) redisData.getData();
//        ArticleDetailVo r = JSONUtil.toBean(data, ArticleDetailVo.class);
//        LocalDateTime expireTime=redisData.getExpireTime();
//        //判断  是否过期
//        if(expireTime.isAfter(LocalDateTime.now())){
//            //未过期 ,直接返回店铺信息
//            return r;
//        }
//        //已过期， 需要缓存重建
//        //缓存重建
//        //获取互斥锁
//        String lockKey=RedisConstants.LOCK_ARTICLE_KEY+id;
//        boolean isLock = tryLock(lockKey);
//        //判断是否获取所成功
//        if(isLock){
//            //再次看一下缓存有没有过期
//            String shopJson=stringRedisTemplate.opsForValue().get(key);
//            RedisData redisData2 = JSONUtil.toBean(shopJson, RedisData.class);
//            JSONObject data2 = (JSONObject) redisData2.getData();
//            ArticleDetailVo r2 = JSONUtil.toBean(data2, ArticleDetailVo.class);
//            LocalDateTime expireTime2=redisData2.getExpireTime();
//            //判断  是否过期
//            if(expireTime.isAfter(LocalDateTime.now())){
//                //未过期 ,直接返回店铺信息
//                return r2;
//            }
//            //成功，开启独立线程 实现缓存重建
//            CACHE_REBUILD_EXECUTOR.submit(()->{
//                try {
//                    //重建缓存
//                    ArticleDetailVo apply = getArticleDetail(id);
//                    this.setWithLogicalExpire(key,apply,20l,TimeUnit.SECONDS);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }finally {
//                    //释放所
//                    unlock(lockKey);
//                }
//            });
//        }
//        //返回过期商品信息
//        return r;
//    }
//    private boolean tryLock(String key){
//        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.MINUTES);
//        return BooleanUtil.isTrue(flag);
//    }
//
//    private boolean unlock(String key){
//        Boolean flag = stringRedisTemplate.delete(key);
//        return BooleanUtil.isTrue(flag);
//    }
//    public void setWithLogicalExpire (String key, Object value, Long time, TimeUnit unit){
//        RedisData redisData=new RedisData();
//        redisData.setData(value);
//        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
//        stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(redisData));
//    }
//
//    @Override
//    public ArticleDetailVo getArticleDetail(Long id) {
//        //根据id查询文章
//        Article article = getById(id);
//        //从redis获得viewCount
//        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
//        article.setViewCount(viewCount.longValue());
//        //转换为VO
//        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
//        //根据分类id查询分类名
//        Long categoryId = articleDetailVo.getCategoryId();
//        Category category=categoryService.getById(categoryId);
//        if(category!=null){
//            articleDetailVo.setCategoryName(category.getName());
//        }
//        //封装响应返回
//        return articleDetailVo;
//    }
//
//
//    @Override
//    public ResponseResult add(AddArticleDto articleDto) {
//        //添加博客
//        Article article=BeanCopyUtils.copyBean(articleDto,Article.class);
//        save(article);
//        //新建博客和标签的关联
//        List<ArticleTag> articleTags=articleDto.getTags().stream()
//                .map(tagId->new ArticleTag(article.getId(),tagId)).collect(Collectors.toList());
//        //更新redis 访问量 0
//        List<Article> articles = getBaseMapper().selectList(null);
//        Map<String , Integer> ViewCountMap = articles.stream()
//                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article1 -> article1.getViewCount().intValue()));
//        //存储到redis
//        redisCache.setCacheMap("article:viewCount",ViewCountMap);
//        //将新关联项存入数据库
//        articleTagService.saveBatch(articleTags);
//        //逻辑过期存入redis
//        article.setViewCount(0l);
//        //转换为VO
//        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
//        //根据分类id查询分类名
//        Long categoryId = articleDetailVo.getCategoryId();
//        Category category=categoryService.getById(categoryId);
//        if(category!=null){
//            articleDetailVo.setCategoryName(category.getName());
//        }
//        this.setWithLogicalExpire(RedisConstants.article_id+articleDetailVo.getId(),articleDetailVo,20l,TimeUnit.SECONDS);
//
//        return ResponseResult.okResult();
//    }
//
//    @Override
//    public ResponseResult listArticle(ListArticleDto listArticleDto) {
//        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
//        if(!listArticleDto.getTitle().equals(""))
//            wrapper.like(Article::getTitle,listArticleDto.getTitle());
//        if(!listArticleDto.getSummary().equals(""))
//            wrapper.like(Article::getSummary,listArticleDto.getSummary());
//        //分页查询
//        Page<Article> page = new Page(listArticleDto.getPageNum(),listArticleDto.getPageSize());
//        page(page,wrapper);
//
//        //List<Article> articles = toCommentVoList(page.getRecords());
//        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
//        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
//    }
//
//
//
//    @Override
//    public ArticleDetailAdminVo queryArticleById(Long id) {
//        //根据id查询文章
//        Article article=getById(id);
//        //从redis获得viewCount
//        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
//        article.setViewCount(viewCount.longValue());
//        //转换为VO
//        ArticleDetailAdminVo articleDetaiAdminVo = BeanCopyUtils.copyBean(article, ArticleDetailAdminVo.class);
//        //查询tags
//        LambdaQueryWrapper<ArticleTag> wrapper=new LambdaQueryWrapper();
//        wrapper.eq(ArticleTag::getArticleId,id);
//        List<ArticleTag> articleTags = articleTagService.list(wrapper);
//        List<Long> tags=new ArrayList<>();
//        for(ArticleTag articleTag:articleTags){
//            tags.add(articleTag.getTagId());
//        }
//        articleDetaiAdminVo.setTags(tags);
//        //封装响应返回
//        return articleDetaiAdminVo;
//    }
//
//    @Override
//    public void updateArticle(ArticleDetailAdminDto articleDetailAdminDto) {
//        //把文章更新到Article表（包括访问量）
//        Article newArticle = BeanCopyUtils.copyBean(articleDetailAdminDto, Article.class);
//        updateById(newArticle);
//        //把文章更新到Article_Tag表
//        //条理先删再加我觉得可
//        List<Long> tags = articleDetailAdminDto.getTags();
//        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId,articleDetailAdminDto.getId()));
//        for(Long tag:tags){
//            articleTagService.save(new ArticleTag(articleDetailAdminDto.getId(),tag));
//        }
//        //把文章访问量更新到redis
//        //查询博客信息
//        List<Article> articles = getBaseMapper().selectList(null);
//        Map<String , Integer> ViewCountMap = articles.stream()
//                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
//        //存储到redis
//        redisCache.deleteObject("article:viewCount");
//        redisCache.setCacheMap("article:viewCount",ViewCountMap);
//        //改变逻辑存储
//        stringRedisTemplate.delete(RedisConstants.article_id+articleDetailAdminDto.getId());
//        newArticle.setViewCount(Long.valueOf(ViewCountMap.get(newArticle.getId().toString())));
//        //转换为VO
//        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(newArticle, ArticleDetailVo.class);
//        //根据分类id查询分类名
//        Long categoryId = articleDetailVo.getCategoryId();
//        Category category=categoryService.getById(categoryId);
//        if(category!=null){
//            articleDetailVo.setCategoryName(category.getName());
//        }
//        this.setWithLogicalExpire(RedisConstants.article_id+articleDetailVo.getId(),articleDetailVo,20l,TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        //逻辑删除 改变del_flag
//        getBaseMapper().deleteById(id);
//        //删除tag联系；
//        articleTagService.deleteTagByArticleId(id);
//        //删除有关评论 逻辑删除
//        commentService.deleteCommentByArticleId(id);
//        //删除redis逻辑
//        stringRedisTemplate.delete(RedisConstants.article_id+id);
//        stringRedisTemplate.opsForHash().delete("article:viewCount",id.toString());
//    }
}
