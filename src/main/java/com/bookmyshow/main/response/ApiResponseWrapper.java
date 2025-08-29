package com.bookmyshow.main.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// Returning true to apply this wrapper to all responses
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// If the response body is already an ApiResponse, don't wrap it again.
		if (body instanceof ApiResponse) {
			return body;
		}

		// If the response is a ResponseEntity, handle it separately
		if (body instanceof ResponseEntity<?>) {
			ResponseEntity<?> entity = (ResponseEntity<?>) body;
			int status = entity.getStatusCode().value();
			Object actualBody = entity.getBody();

			// Handle case where there is no body (empty content)
			if (actualBody == null) {
				return ResponseEntity.status(status).body(new ApiResponse<>(status, "No content", false, null));
			}

			// Wrap the body with ApiResponse for success responses
			return ResponseEntity.status(status).body(new ApiResponse<>(status, "Success", true, actualBody));
		}

		// For any other object, wrap it in a successful ApiResponse
		return new ApiResponse<>(200, "Success", true, body);
	}
}
