package ua.nure.library.dto;

import java.util.Collection;

/**
 * PageDto
 *
 * @param <T> class type
 */
public class PageDto<T> {

    private Collection<T> content;
    private long itemsPerPage;
    private long pageNumber;
    private long totalItems;
    private long totalPages;

    public PageDto() {
    }

    public PageDto(Collection<T> content) {
        this.content = content;
    }

    public Collection<T> getContent() {
        return content;
    }

    public PageDto<T> setContent(Collection<T> content) {
        this.content = content;
        return this;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public PageDto<T> setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public PageDto<T> setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public PageDto<T> setTotalItems(long totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public PageDto<T> setTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }
}
