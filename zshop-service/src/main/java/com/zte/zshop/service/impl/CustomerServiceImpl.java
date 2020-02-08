package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.constant.Constants;
import com.zte.zshop.dao.CustomerDao;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.exception.CustomerHasRegistException;
import com.zte.zshop.exception.LoginErrorExcpetion;
import com.zte.zshop.exception.PhoneNotFoundException;
import com.zte.zshop.params.CustomerParam;
import com.zte.zshop.service.CustomerService;
import com.zte.zshop.vo.CustomerVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author:helloboy
 * Date:2019-06-05 11:05
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerDao customerDao;

    @Override
    public PageInfo<Customer> findAll(Integer pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Customer> customers = customerDao.selectAll();

        PageInfo<Customer> pageInfo = new PageInfo<>(customers);

        return pageInfo;
    }

    @Override
    public Customer findById(Integer id) {

        return customerDao.selectById(id);
    }

    @Override
    public Customer modifyCustomer(CustomerVo customerVo) {

        Customer customer = new Customer();

        try {

            PropertyUtils.copyProperties(customer, customerVo);
            customer.setId(customerDao.selectByName(customerVo.getLoginName()).getId());
            customerDao.update(customer);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void changeStatus(Integer id) {

        Customer customer = customerDao.selectById(id);
        Integer isValid = customer.getIsValid();
        if (isValid == Constants.SYSUSER_VALID) {
            isValid = Constants.SYSUSER_INVALID;
        } else {
            isValid = Constants.SYSUSER_VALID;
        }
        customerDao.updateStatus(id, isValid);
    }

    @Override
    public List<Customer> findByParam(CustomerParam customerParam) {


        return customerDao.selectByParam(customerParam);
    }

    @Override
    public Customer loginByNameAndPass(String loginName, String password) throws LoginErrorExcpetion {

        Customer customer = customerDao.selectByNameAndPass(loginName, password, Constants.CUSTOMER_VALID);

        if (customer == null) {
            throw new LoginErrorExcpetion("登录失败,用户名或密码错误");
        }
        return customer;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Customer findByPhone(String phone) throws PhoneNotFoundException {
        Customer customer = customerDao.selectByPhone(phone);
        if (customer == null) {

            throw new PhoneNotFoundException("该手机号尚未注册");
        }
        return customer;
    }

    @Override
    public void addCustomer(CustomerVo customerVo) throws CustomerHasRegistException {
        Customer customer = new Customer();

        try {
            PropertyUtils.copyProperties(customer, customerVo);

            customer.setRegistDate(new Date());
            customer.setIsValid(1);
            customer.setMember(0);
            //检查用户名是否注册
            Customer IsRegistLogin_Name = customerDao.selectByName(customerVo.getLoginName());

            if (IsRegistLogin_Name ==null) {
                //用户名未注册
                customerDao.insertCustomer(customer);
            } else {
                throw new CustomerHasRegistException("用户名已注册");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkName(String loginName) {
        Customer customer = new Customer();
        customer = customerDao.selectByName(loginName);
        if(customer == null){
            return true;
        }
        return  false;
    }

    @Override
    public boolean checkOldPwd(String oldpwd, Integer id) {
        Customer customer = customerDao.selectById(id);
        if(customer.getPassword().equals(oldpwd)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNewPwd(String newpwd, Integer id) {
        Customer customer = customerDao.selectById(id);
        if(customer.getPassword().equals(newpwd)){
            return false;
        }
        return true;
    }

    @Override
    public void modifyPassword(String newpwd, Integer id) {
        customerDao.updatePasswordById(newpwd,id);
    }

    @Override
    public Customer modifyMemberById(Integer id) {
        Customer customer = customerDao.selectById(id);
        customer.setMember(1);
        customerDao.update(customer);
        return customer;
    }

    @Override
    public Customer findByLoginName(String login_name) {
        Customer customer = new Customer();
        customer = customerDao.selectByName(login_name);
        return customer;
    }


}
















