package com.vteba.batch.send.action;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.batch.send.model.Send;
import com.vteba.batch.send.service.spi.SendService;
import com.vteba.cache.redis.JedisTemplate;
import com.vteba.cache.redis.RedisService;
import com.vteba.web.action.GenericAction;
import com.vteba.web.action.JsonBean;

/**
 * 发送控制器
 * @author yinlei
 * @date 2016-3-22 16:10:39
 */
@Controller
@RequestMapping("/send")
public class SendAction extends GenericAction<Send> {
	private static final Logger LOGGER = LogManager.getLogger(SendAction.class);
	
	@Inject
	private SendService sendServiceImpl;
	@Inject
	private JedisTemplate jedisTemplate;
	@Inject
	private RedisService<String, Send> redisService;
	
	/**
     * 获得发送List，初始化列表页。
     * @param model 参数
     * @return 发送List
     */
    @RequestMapping("/initial")
    public String initial(Send model, Map<String, Object> maps) {
    	try {
    		model.setId(1222);
    		model.setName("尹雷");
    		model.setPageSize(10);
    		long d = System.currentTimeMillis();
    		redisService.set("yinlei2", model);
    		redisService.get("yinlei2");
    		System.out.println(System.currentTimeMillis() - d);
    		
    		model.setId(32112);
    		model.setName("好家伙");
    		model.setPageSize(35);
    		d = System.currentTimeMillis();
    		jedisTemplate.set("yinlei1", model);
    		jedisTemplate.get("yinlei1", Send.class);
    		System.out.println(System.currentTimeMillis() - d);
    		
    		List<Send> list = sendServiceImpl.pagedList(model);
            maps.put("list", list);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "send/initial";
    }
	
	/**
	 * 获得发送List，Json格式。
	 * @param model 参数
	 * @return 发送List
	 */
	@ResponseBody
	@RequestMapping("/list")
	public List<Send> list(Send model) {
		List<Send> list = null;
		try {
			list = sendServiceImpl.pagedList(model);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
		}
		return list;
	}
	
	/**
     * 根据Id获得发送实体，Json格式。
     * @param id 参数id
     * @return 发送实体
     */
    @ResponseBody
    @RequestMapping("/get")
    public Send get(Integer id) {
    	Send model = null;
    	try {
    		model = sendServiceImpl.get(id);
		} catch (Exception e) {
			LOGGER.error("get record error, errorMsg=[{}].", e.getMessage());
		}
        return model;
    }
	
	/**
     * 跳转到新增页面
     * @return 新增页面逻辑视图
     */
    @RequestMapping("/add")
    public String add() {
        return "send/add";
    }
    
    /**
     * 执行实际的新增操作
     * @param model 要新增的数据
     * @return 新增页面逻辑视图
     */
    @ResponseBody
    @RequestMapping("/doAdd")
    public JsonBean doAdd(Send model) {
    	JsonBean bean = new JsonBean();
    	try {
    		model.setName("ygg");
    		int result = sendServiceImpl.saveSend(model);//sendServiceImpl.save(model);
            if (result == 1) {
                bean.setMessage(SUCCESS);
                bean.setCode(1);
                LOGGER.info("save record success.");
            } else {
                bean.setMessage(ERROR);
                LOGGER.error("save record error.");
            }
		} catch (Exception e) {
			LOGGER.error("save record error, errorMsg=[{}].", e.getMessage());
			bean.setMessage(ERROR);
		}
        return bean;
    }
    
    /**
     * 查看发送详情页。
     * @param model 查询参数，携带ID
     * @return 发送详情页
     */
    @RequestMapping("/detail")
    public String detail(Send model, Map<String, Object> maps) {
    	try {
    		model = sendServiceImpl.get(model.getId());
            maps.put("model", model);//将model放入视图中，供页面视图使用
		} catch (Exception e) {
			LOGGER.error("query record detail, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "send/detail";
    }
    
    /**
     * 跳转到编辑信息页面
     * @param model 查询参数，携带ID
     * @return 编辑页面
     */
    @RequestMapping("/edit")
    public String edit(Send model, Map<String, Object> maps) {
        model = sendServiceImpl.get(model.getId());
        maps.put("model", model);
        return "send/edit";
    }
    
    /**
     * 更新发送信息
     * @param model 要更新的发送信息，含有ID
     * @return 操作结果信息
     */
    @ResponseBody
    @RequestMapping("/update")
    public JsonBean update(Send model) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = sendServiceImpl.updateById(model);
            if (result == 1) {
                bean.setMessage(SUCCESS);
                bean.setCode(1);
                LOGGER.info("update record success.");
            } else {
                bean.setMessage(ERROR);
                LOGGER.error("update record error.");
            }
		} catch (Exception e) {
			LOGGER.error("update record error, errorMsg=[{}].", e.getMessage());
			bean.setMessage(ERROR);
		}
        return bean;
    }
    
    /**
     * 删除发送信息
     * @param id 要删除的发送ID
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JsonBean delete(Integer id) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = sendServiceImpl.deleteById(id);
    		if (result == 1) {
    			bean.setMessage(SUCCESS);
    			bean.setCode(1);
    			LOGGER.info("delete record success, id=[{}].", id);
    		} else {
    			bean.setMessage(ERROR);
    			LOGGER.error("delete record error.");
    		}
		} catch (Exception e) {
			LOGGER.error("delete record error, id=[{}], errorMsg=[{}].", id, e.getMessage());
			bean.setMessage(ERROR);
		}
        return bean;
    }
}
