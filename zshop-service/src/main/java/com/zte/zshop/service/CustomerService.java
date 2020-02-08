package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.exception.CustomerHasRegistException;
import com.zte.zshop.exception.LoginErrorExcpetion;
import com.zte.zshop.exception.PhoneNotFoundException;
import com.zte.zshop.params.CustomerParam;
import com.zte.zshop.vo.CustomerVo;

import java.util.List;

/**
 * Author:helloboy
 * Date:2019-06-05 11:05
 * Description:<描述>
 */
public interface CustomerService {

    public PageInfo<Customer> findAll(Integer pageNum, int pageSize);

    public Customer findById(Integer id);

    public Customer modifyCustomer(CustomerVo customerVo);

    public void changeStatus(Integer id);

    public List<Customer> findByParam(CustomerParam customerParam);

    public Customer loginByNameAndPass(String loginName, String password)throws LoginErrorExcpetion;
    Customer findByPhone(String phone)throws PhoneNotFoundException;

    public void addCustomer(CustomerVo customerVo) throws CustomerHasRegistException;

    public boolean checkName(String name);

    public boolean checkOldPwd(String oldpwd, Integer id);

    public boolean checkNewPwd(String newpwd, Integer id);

    public void modifyPassword(String newpwd, Integer id);

    public Customer modifyMemberById(Integer id);

    public Customer findByLoginName(String login_name);
}
