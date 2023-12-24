package com.huang.Dto;

import com.huang.Annotation.SmartValidation;
import com.huang.Enum.ValidationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: Blog
 * @author: hjw
 * @create: 2023-12-21 22:02
 * @ClassName:LoginDto
 * @Description:
 **/

@ApiModel("登录实体类")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @ApiModelProperty("用户名")
    @SmartValidation(type = ValidationType.USERNAME)
    private String username;

    @ApiModelProperty("密码")
    @SmartValidation(message = "验证失败",type = ValidationType.PASSWORD)
    private String password;

}
