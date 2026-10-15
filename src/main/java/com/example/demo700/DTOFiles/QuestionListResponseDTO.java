package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionListResponseDTO implements Serializable {

	private static final long serialVersionUID = 10002L;

	List<QuestionResponse> responses = new ArrayList<>();

	private int page, size;
	private long totalElement;

	private String nextPage;

	private String previousPage;

	public QuestionListResponseDTO(List<QuestionResponse> responses, int page, int size, long totalElement,
			String nextPage, String previousPage) {
		super();
		this.responses = responses;
		this.page = page;
		this.size = size;
		this.totalElement = totalElement;
		this.nextPage = nextPage;
		this.previousPage = previousPage;
	}

	public QuestionListResponseDTO() {
		super();
	}

	public List<QuestionResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<QuestionResponse> responses) {
		this.responses = responses;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	@Override
	public String toString() {
		return "QuestionListResponseDTO [responses=" + responses + ", page=" + page + ", size=" + size
				+ ", totalElement=" + totalElement + ", nextPage=" + nextPage + ", previousPage=" + previousPage + "]";
	}

}
