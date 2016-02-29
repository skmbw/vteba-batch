package com.vteba.batch.user.action;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.batch.user.model.User;
import com.vteba.batch.user.service.spi.UserService;
import com.vteba.mq.rocketmq.producer.RocketMQMessageProducer;
import com.vteba.utils.id.IntIncrement;
import com.vteba.utils.serialize.Kryoer;
import com.vteba.web.action.GenericAction;
import com.vteba.web.action.JsonBean;

/**
 * 用户控制器
 * @author yinlei
 * @date 2016-2-18 16:34:16
 */
@Controller
@RequestMapping("/user")
public class UserAction extends GenericAction<User> {
	private static final Logger LOGGER = LogManager.getLogger(UserAction.class);
	
	@Inject
	private UserService userServiceImpl;
	
	@Inject
	private Kryoer kryoer;
	
	@Inject
	private RocketMQMessageProducer rocketMQMessageProducer;
	
	/**
     * 获得用户List，初始化列表页。
     * @param model 参数
     * @return 用户List
     */
    @RequestMapping("/initial")
    public String initial(User model, Map<String, Object> maps) {
    	try {
    		model.setName("yinlei");
    		model.setAge(12);
    		long d = System.currentTimeMillis();
    		byte[] bb = kryoer.toByte(model);
    		System.out.println(System.currentTimeMillis() - d);
    		d = System.currentTimeMillis();
    		kryoer.fromByte(bb);
    		System.out.println(System.currentTimeMillis() - d);
    		
    		List<User> list = userServiceImpl.pagedList(model);
            maps.put("list", list);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "user/initial";
    }
	
	/**
	 * 获得用户List，Json格式。
	 * @param model 参数
	 * @return 用户List
	 */
	@ResponseBody
	@RequestMapping("/list")
	public List<User> list(User model) {
		List<User> list = null;
		try {
			model = new User();
			model.setName("Test1");
			
			rocketMQMessageProducer.send(model);
			model.setAge(29);
			rocketMQMessageProducer.send("Test1", model);
			
			model.setId(2345);
			rocketMQMessageProducer.send("YinleiUser2", "Test2", model);
			//list = userServiceImpl.pagedList(model);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
		}
		return list;
	}
	
	/**
     * 根据Id获得用户实体，Json格式。
     * @param id 参数id
     * @return 用户实体
     */
    @ResponseBody
    @RequestMapping("/get")
    public User get(Integer id) {
    	User model = null;
    	try {
    		//model = userServiceImpl.get(id);
    		userServiceImpl.updateEntity(1);
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
    	userServiceImpl.updateUser(IntIncrement.getAndInc());
        return "user/add";
    }
    
    /**
     * 执行实际的新增操作
     * @param model 要新增的数据
     * @return 新增页面逻辑视图
     */
    @ResponseBody
    @RequestMapping("/doAdd")
    public JsonBean doAdd(User model) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = userServiceImpl.save(model);
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
     * 查看用户详情页。
     * @param model 查询参数，携带ID
     * @return 用户详情页
     */
    @RequestMapping("/detail")
    public String detail(User model, Map<String, Object> maps) {
    	try {
    		model = userServiceImpl.get(model.getId());
            maps.put("model", model);//将model放入视图中，供页面视图使用
		} catch (Exception e) {
			LOGGER.error("query record detail, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "user/detail";
    }
    
    /**
     * 跳转到编辑信息页面
     * @param model 查询参数，携带ID
     * @return 编辑页面
     */
    @RequestMapping("/edit")
    public String edit(User model, Map<String, Object> maps) {
        model = userServiceImpl.get(model.getId());
        maps.put("model", model);
        return "user/edit";
    }
    
    /**
     * 更新用户信息
     * @param model 要更新的用户信息，含有ID
     * @return 操作结果信息
     */
    @ResponseBody
    @RequestMapping("/update")
    public JsonBean update(User model) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = userServiceImpl.updateById(model);
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
     * 删除用户信息
     * @param id 要删除的用户ID
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JsonBean delete(Integer id) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = userServiceImpl.deleteById(id);
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
