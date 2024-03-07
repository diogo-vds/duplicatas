package com.crdc.desafio.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class UploadResponseDTO {
	
		  private String status;
		  private String message;
		  @JsonInclude(JsonInclude.Include.NON_NULL)
		  private DataDTO data;
		  @JsonInclude(JsonInclude.Include.NON_NULL)
		  private List<ErrorDTO> errors;
}
