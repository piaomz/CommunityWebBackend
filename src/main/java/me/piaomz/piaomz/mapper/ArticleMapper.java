package me.piaomz.piaomz.mapper;

import me.piaomz.piaomz.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    Article queryArticle(int articleid);
    List<Article> selectArticleByUID(int uid);
    int insertArticle(@Param("article") Article article, int uid, Date date,String title,String content,String type,String imageurl);
    int deleteArticle(int articleid);
    int updateArticle(int articleid, int uid,Date date,String title,String content,String type,String imageurl);

    List<Favorite> selectFavoriteByUID(int uid);
    void favoriteArticle(int uid,int articleid,Date time);
    void deleteFavorite(int uid,int articleid);

    List<Comment> selectCommentByArticleID(int articleid);
    Comment queryComment(int commentid);
    void insertComment(Comment comment);
    void deleteComment(int commentid);

    List<CommentFavorite> selectCommentFavoriteByUIDAndCommentId(int uid,int commentid);
    List<CommentFavorite> selectCommentFavoriteByCommentId(int commentid);
    void favoriteComment(CommentFavorite commentFavorite);
    void deleteCommentFavorite(int uid, int commentid);

    List<HistoryArticle> selectHistoryArticleByUid(int uid);
    void insertHistoryArticle(HistoryArticle historyArticle);
    void clearHistoryArticle(int uid);

}
