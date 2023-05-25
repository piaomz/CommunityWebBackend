package me.piaomz.piaomz.controller;

import me.piaomz.piaomz.mapper.ArticleMapper;
import me.piaomz.piaomz.mapper.UserMapper;
import me.piaomz.piaomz.pojo.Article;
import me.piaomz.piaomz.pojo.User;
import me.piaomz.piaomz.service.ArticleService;
import me.piaomz.piaomz.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/article/new")
    public ResponseResult newArticle(@RequestParam(value = "title", defaultValue = "") String title,
                                     @RequestParam(value = "content", defaultValue = "") String content,
                                     @RequestParam(value = "type", defaultValue = "") String type,
                                     @RequestParam(value = "imageURL", defaultValue = "") String imageUrl){
        return articleService.newArticle(title,content,type,imageUrl);
    }
    @GetMapping("/article/delete")
    public ResponseResult deleteArticle(@RequestParam(value = "articleid", defaultValue = "") String articleid){
        return articleService.deleteArticle(articleid);
    }
    @GetMapping("/article/query")
    public ResponseResult queryArticle(@RequestParam(value = "articleid", defaultValue = "") String articleid){
        return articleService.queryArticle(articleid);
    }
    @GetMapping("/article/update")
    public ResponseResult updateArticle(
                                    @RequestParam(value = "articleid", defaultValue = "") String articleid,
                                    @RequestParam(value = "title", defaultValue = "") String title,
                                     @RequestParam(value = "content", defaultValue = "") String content,
                                     @RequestParam(value = "type", defaultValue = "") String type,
                                     @RequestParam(value = "imageURL", defaultValue = "") String imageUrl){
        return articleService.updateArticle(articleid,title,content,type,imageUrl);
    }
    @GetMapping("/article/selectByUid")
    public ResponseResult selectByUid(@RequestParam(value = "uid", defaultValue = "") String uid){
        return articleService.selectArticleByUID(uid);
    }
    @GetMapping("/article/selectFavoriteByUid")
    public ResponseResult selectFavoriteByUid(@RequestParam(value = "uid", defaultValue = "") String uid){
        return articleService.selectFavoriteByUID(uid);
    }

    @GetMapping("/article/favoriteArticle")
    public ResponseResult favoriteArticle(@RequestParam(value = "articleid", defaultValue = "") String articleid){
        return articleService.favoriteArticle(articleid);
    }
    @GetMapping("/article/deleteFavoriteArticle")
    public ResponseResult deleteFavoriteArticle(@RequestParam(value = "articleid", defaultValue = "") String articleid){
        return articleService.deleteFavoriteArticle(articleid);
    }
    @GetMapping("/article/selectComment")
    public ResponseResult selectComment(@RequestParam(value = "articleid", defaultValue = "") String articleid){
        return articleService.getCommentsOfArticle(articleid);
    }
    @GetMapping("/article/newComment")
    public ResponseResult newComment(@RequestParam(value = "articleid", defaultValue = "") String articleid,
                                     @RequestParam(value = "content", defaultValue = "") String content){
        return articleService.newComment(articleid,content);
    }
    @GetMapping("/article/deleteComment")
    public ResponseResult deleteComment(@RequestParam(value = "commentid", defaultValue = "") String commentid){
        return articleService.deleteComment(commentid);
    }

    @GetMapping("/article/selectHistory")
    public ResponseResult selectHistory(){
        return articleService.getHistory();
    }
    @GetMapping("/article/clearHistory")
    public ResponseResult clearHistory(){
        return articleService.clearHistory();
    }

    @GetMapping("/article/favoriteComment")
    public ResponseResult favoriteComment(@RequestParam(value = "commentid", defaultValue = "") String commentid){
        return articleService.favoriteComment(commentid);
    }

    @GetMapping("article/selectCommentFavorite")
    public ResponseResult selectCommentFavorite(@RequestParam(value = "uid", defaultValue = "") String uid,
                                                @RequestParam(value = "commentid", defaultValue = "") String commentid){
        return articleService.selectCommentFavoriteByUIDAndCommentId(uid, commentid);
    }
    @GetMapping("article/deleteCommentFavorite")
    public ResponseResult deleteCommentFavorite(@RequestParam(value = "uid", defaultValue = "") String uid,
                                                @RequestParam(value = "commentid", defaultValue = "") String commentid){
        return articleService.deleteCommentFavorite(uid,commentid);
    }




}
