package com.fyp.fly.services.comment.domain.dto;

/**
 * @author fyp
 * @crate 2019/3/21 22:00
 * @project fly
 */
public class CommentListDto {
    private Long artId;
    private Long authorId;
    private Long userId;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public  boolean isMine() {
        return authorId.equals(userId);
    }

    private Integer pageIndex;
    private Integer pageSize;

    public Integer getStart(){
        if (pageIndex== null||pageIndex<0){
            pageIndex=1;
        }
        if (pageSize==null||pageSize<=0||pageSize>100) {
            pageSize = 20;
        }
        return (pageIndex-1)*pageSize;
    }

    public Integer getEnd(){
        return getStart()+ pageSize;
    }
}
