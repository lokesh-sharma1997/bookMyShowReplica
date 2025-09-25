package com.bookmyshow.main.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPageResponse<T> {
	private long count;
    private List<T> content;
    
}