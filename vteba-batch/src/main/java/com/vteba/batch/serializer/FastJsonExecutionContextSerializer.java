package com.vteba.batch.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.repository.ExecutionContextSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 使用fastjson进行上下文的序列化和发序列化
 * 
 * @author yinlei
 * @date 2016年3月9日 下午1:58:40
 */
public class FastJsonExecutionContextSerializer implements ExecutionContextSerializer {

	@Override
	public void serialize(Map<String, Object> object, OutputStream outputStream) throws IOException {
		byte[] bytes = JSON.toJSONBytes(object);
		outputStream.write(bytes);
	}

	@Override
	public Map<String, Object> deserialize(InputStream inputStream) throws IOException {
		String json = IOUtils.toString(inputStream);
		Map<String, Object> context = JSON.parseObject(json, new TypeReference<Map<String, Object>>(){});
		return context;
	}

}