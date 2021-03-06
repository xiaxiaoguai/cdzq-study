/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cdzq.study.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public class BadRequestException extends RuntimeException{

    private Integer http_code = OK.value();
    private Integer result_code = -1;
    private String message;

    public BadRequestException(String msg){
        this.message = msg;
    }

    public BadRequestException(HttpStatus status, Integer result_code, String msg){
        this.http_code = status.value();
        this.http_code = result_code;
        this.message = msg;
    }
}
