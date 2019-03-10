package com.mapscience.modular.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mapscience.modular.system.mapper.ErrorLogMapper;
import com.mapscience.modular.system.model.ErrorLog;
import com.mapscience.modular.system.service.IErrorLogService;

/**
 * <p>
 * 接口错误报错 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-02-26
 */
@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog> implements IErrorLogService {


}
