package me.piaomz.piaomz.service;

import me.piaomz.piaomz.mapper.ArticleMapper;
import me.piaomz.piaomz.pojo.*;
import me.piaomz.piaomz.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult newArticle(String title, String content, String type, String imageUrl) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        Article article = new Article();
        articleMapper.insertArticle(article, uid,new Date(),title,content,type,imageUrl);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("articleID",article.getArticleid());
        return new ResponseResult(200,result);
    }

    @Override
    public ResponseResult deleteArticle(String articleid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        try{
            Article article = articleMapper.queryArticle(Integer.parseInt(articleid));
            if(Objects.isNull(article)){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid not found");
            }
            if(article.getUid()!=uid && !loginUserDetails.getUser().getType().equals("admin")){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid not yours");
            }
            articleMapper.deleteArticle(Integer.parseInt(articleid));
            return new ResponseResult(HttpStatus.OK.value(), "delete article successful");
            //return null;
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }
    }

    @Override
    public ResponseResult queryArticle(String articleid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        try{
            Article article = articleMapper.queryArticle(Integer.parseInt(articleid));
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("article",article);
            HistoryArticle historyArticle = new HistoryArticle();
            historyArticle.setTime(new Date());
            historyArticle.setUid(uid);
            historyArticle.setArticleid(Integer.parseInt(articleid));
            articleMapper.insertHistoryArticle(historyArticle);
            return new ResponseResult(HttpStatus.OK.value(), result);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }

    }

    @Override
    public ResponseResult selectArticleByUID(String uid) {
        try{
            List<Article> articles = articleMapper.selectArticleByUID(Integer.parseInt(uid));
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("articles",articles);
            return new ResponseResult(HttpStatus.OK.value(), result);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
    }

    @Override
    public ResponseResult updateArticle(String articleid, String title, String content, String type, String imageUrl) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        try{
            Article article = articleMapper.queryArticle(Integer.parseInt(articleid));
            if(Objects.isNull(article)){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid not found");
            }
            if(article.getUid()!=uid && !loginUserDetails.getUser().getType().equals("admin")){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid not yours");
            }
            articleMapper.updateArticle(Integer.parseInt(articleid),article.getUid(),new Date(),title,content,type,imageUrl);
            return new ResponseResult(HttpStatus.OK.value(), "update article successful");
            //return null;
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }
    }

    @Override
    public ResponseResult selectFavoriteByUID(String uid) {
// handle illegal prams
        int parseuid;
        try{
            parseuid =Integer.parseInt(uid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid illegal");
        }
        List<Favorite> favos = articleMapper.selectFavoriteByUID(parseuid);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("favorites",favos);
        return new ResponseResult<>(HttpStatus.OK.value(), result);
    }

    @Override
    public ResponseResult favoriteArticle(String articleid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        // handle illegal prams
        int parseArticleid;
        try{
            parseArticleid =Integer.parseInt(articleid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }

        try{
            articleMapper.favoriteArticle(uid,parseArticleid,new Date());
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), e.getMessage());
        }
        return new ResponseResult(HttpStatus.OK.value(), "favorite article successful");
    }

    @Override
    public ResponseResult deleteFavoriteArticle(String articleid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        // handle illegal prams
        int parseArticleid;
        try{
            parseArticleid =Integer.parseInt(articleid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }
        articleMapper.deleteFavorite(uid,parseArticleid);
        return new ResponseResult(HttpStatus.OK.value(), "delete favorite article successful");
    }

    @Override
    public ResponseResult getCommentsOfArticle(String articleid) {
        // handle illegal prams
        int parseArticleid;
        try{
            parseArticleid =Integer.parseInt(articleid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }
        List<Comment> comments=articleMapper.selectCommentByArticleID(parseArticleid);
        return new ResponseResult<>(HttpStatus.OK.value(), comments);
    }

    @Override
    public ResponseResult newComment(String articleid, String content) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int uid = loginUserDetails.getUser().getUid();
        int parseArticleid;
        try{
            parseArticleid =Integer.parseInt(articleid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "articleid illegal");
        }
        Comment comment = new Comment(parseArticleid,uid,content, new Date());
        try{
            articleMapper.insertComment(comment);
        }catch(Exception e){
            return new ResponseResult<>(HttpStatus.FORBIDDEN.value(), e.getMessage());
        }
        return new ResponseResult<>(HttpStatus.OK.value(), comment);
    }


    @Override
    public ResponseResult deleteComment(String commentid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        int parseCommentid;
        try{
            parseCommentid =Integer.parseInt(commentid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "commentid illegal");
        }
        Comment comment=articleMapper.queryComment(parseCommentid);
        if(Objects.isNull(comment)){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "CommentId not found");
        }else if(comment.getUid()!=tokenuid && !loginUserDetails.getUser().getType().equals("admin")){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "Not permit to delete others comment");
        }else{
            articleMapper.deleteComment(parseCommentid);
            return new ResponseResult(HttpStatus.OK.value(), "Delete comment successful!");
        }
    }

    @Override
    public ResponseResult getHistory() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        List<HistoryArticle> historyArticles=articleMapper.selectHistoryArticleByUid(tokenuid);
        return new ResponseResult<>(HttpStatus.OK.value(), historyArticles);
    }

    @Override
    public ResponseResult clearHistory() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();
        articleMapper.clearHistoryArticle(tokenuid);
        return new ResponseResult(HttpStatus.OK.value(), "clear all history successful");
    }

    @Override
    public ResponseResult selectCommentFavoriteByUIDAndCommentId(String uid, String commentid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();

        int parseuid, parseCommentid;
        try{
            parseuid = Integer.parseInt(uid);
            parseCommentid =Integer.parseInt(commentid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid or commentid illegal");
        }

        if(!loginUserDetails.getUser().getType().equals("admin") && parseuid!=tokenuid){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "not permit to read others uid");
        }

        List<CommentFavorite> commentFavorites=articleMapper.selectCommentFavoriteByUIDAndCommentId(parseuid,parseCommentid);
        List<CommentFavorite> commentFavorites2=articleMapper.selectCommentFavoriteByCommentId(parseCommentid);
        HashMap<String,Object> data = new HashMap<>();
        data.put("favoriteCount",commentFavorites2.size());
        if(commentFavorites.size()!=0){
            data.put("isFavortieComment",true);
        }else{
            data.put("isFavortieComment",false);
        }
        return new ResponseResult(HttpStatus.OK.value(), data);
    }

    @Override
    public ResponseResult favoriteComment(String commentid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();

        int  parseCommentid;
        try{
            parseCommentid =Integer.parseInt(commentid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid or commentid illegal");
        }

        CommentFavorite commentFavorite = new CommentFavorite();
        commentFavorite.setCommentid(parseCommentid);
        commentFavorite.setTime(new Date());
        commentFavorite.setUid(tokenuid);
        try{
            articleMapper.favoriteComment(commentFavorite);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "cannot favorite this comment, maybe already favorite comment, maybe commentid not found");
        }
        return new ResponseResult(HttpStatus.OK.value(),"Favorite Comment Successful");
    }

    @Override
    public ResponseResult deleteCommentFavorite(String uid, String commentid) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails =(LoginUserDetails) token.getPrincipal();
        int tokenuid = loginUserDetails.getUser().getUid();

        int parseuid, parseCommentid;
        try{
            parseuid = Integer.parseInt(uid);
            parseCommentid =Integer.parseInt(commentid);
        }catch(Exception e){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "uid or commentid illegal");
        }

        if(!loginUserDetails.getUser().getType().equals("admin") && parseuid!=tokenuid){
            return new ResponseResult(HttpStatus.FORBIDDEN.value(), "not permit to modify others uid");
        }

        articleMapper.deleteCommentFavorite(parseuid,parseCommentid);
        return new ResponseResult(HttpStatus.OK.value(),"Delete Favorite Comment Successful");
    }
}
