package com.vteba.batch.investor.action;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.batch.investor.model.Investor;
import com.vteba.batch.investor.service.spi.InvestorService;
import com.vteba.utils.id.IdsGenerator;
import com.vteba.web.action.GenericAction;
import com.vteba.web.action.JsonBean;

/**
 * 投资人控制器
 * @author yinlei
 * @date 2016-3-10 14:08:23
 */
@Controller
@RequestMapping("/investor")
public class InvestorAction extends GenericAction<Investor> {
	private static final Logger LOGGER = LogManager.getLogger(InvestorAction.class);
	
	@Inject
	private InvestorService investorServiceImpl;
	
	@Inject
	private IdsGenerator idsGenerator;
	
	/**
     * 获得投资人List，初始化列表页。
     * @param model 参数
     * @return 投资人List
     */
    @RequestMapping("/initial")
    public String initial(Investor model, Map<String, Object> maps) {
    	try {
    		String id = idsGenerator.get();
    		LOGGER.info(id);
    		List<Investor> list = investorServiceImpl.pagedList(model);
            maps.put("list", list);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "investor/initial";
    }
	
	/**
	 * 获得投资人List，Json格式。
	 * @param model 参数
	 * @return 投资人List
	 */
	@ResponseBody
	@RequestMapping("/list")
	public List<Investor> list(Investor model) {
		List<Investor> list = null;
		try {
			list = investorServiceImpl.pagedList(model);
		} catch (Exception e) {
			LOGGER.error("get record list error, errorMsg=[{}].", e.getMessage());
		}
		return list;
	}
	
	/**
     * 根据Id获得投资人实体，Json格式。
     * @param id 参数id
     * @return 投资人实体
     */
    @ResponseBody
    @RequestMapping("/get")
    public Investor get(Integer id) {
    	Investor model = null;
    	try {
    		model = investorServiceImpl.get(id);
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
        return "investor/add";
    }
    
    /**
     * 执行实际的新增操作
     * @param model 要新增的数据
     * @return 新增页面逻辑视图
     */
    @ResponseBody
    @RequestMapping("/doAdd")
    public JsonBean doAdd(Investor model) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = investorServiceImpl.save(model);
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
     * 查看投资人详情页。
     * @param model 查询参数，携带ID
     * @return 投资人详情页
     */
    @RequestMapping("/detail")
    public String detail(Investor model, Map<String, Object> maps) {
    	try {
    		model = investorServiceImpl.get(model.getId());
            maps.put("model", model);//将model放入视图中，供页面视图使用
		} catch (Exception e) {
			LOGGER.error("query record detail, errorMsg=[{}].", e.getMessage());
			return "common/error";
		}
        return "investor/detail";
    }
    
    /**
     * 跳转到编辑信息页面
     * @param model 查询参数，携带ID
     * @return 编辑页面
     */
    @RequestMapping("/edit")
    public String edit(Investor model, Map<String, Object> maps) {
        model = investorServiceImpl.get(model.getId());
        maps.put("model", model);
        return "investor/edit";
    }
    
    /**
     * 更新投资人信息
     * @param model 要更新的投资人信息，含有ID
     * @return 操作结果信息
     */
    @ResponseBody
    @RequestMapping("/update")
    public JsonBean update(Investor model) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = investorServiceImpl.updateById(model);
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
     * 删除投资人信息
     * @param id 要删除的投资人ID
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JsonBean delete(Integer id) {
    	JsonBean bean = new JsonBean();
    	try {
    		int result = investorServiceImpl.deleteById(id);
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
