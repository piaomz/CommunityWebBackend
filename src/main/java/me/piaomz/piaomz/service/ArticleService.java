package me.piaomz.piaomz.service;

import me.piaomz.piaomz.pojo.Comment;
import me.piaomz.piaomz.pojo.CommentFavorite;
import me.piaomz.piaomz.utils.ResponseResult;

import java.util.ResourceBundle;

public interface ArticleService {
    ResponseResult newArticle(String title, String content, String type, String imageUrl);

    ResponseResult deleteArticle(String articleid);

    ResponseResult queryArticle(String articleid);

    ResponseResult selectArticleByUID(String uid);

    ResponseResult updateArticle(String articleid, String title, String content, String type, String imageUrl);

    ResponseResult selectFavoriteByUID(String uid);

    ResponseResult favoriteArticle(String articleid);

    ResponseResult deleteFavoriteArticle(String articleid);

    ResponseResult getCommentsOfArticle(String articleid);

    ResponseResult newComment(String articleid,String content);

    ResponseResult deleteComment(String commentid);

    ResponseResult getHistory();

    ResponseResult clearHistory();

    ResponseResult selectCommentFavoriteByUIDAndCommentId(String uid,String commentid);

    ResponseResult favoriteComment(String commentid);
    ResponseResult deleteCommentFavorite(String uid, String commentid);
}
