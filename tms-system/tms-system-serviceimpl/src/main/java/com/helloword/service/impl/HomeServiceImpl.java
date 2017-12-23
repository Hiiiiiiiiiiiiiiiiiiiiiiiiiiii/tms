package com.helloword.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.helloword.entity.*;
import com.helloword.mapper.*;
import com.helloword.service.HomeService;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.List;
@Service
public class HomeServiceImpl  implements HomeService{

    //qiniu
    private static final String  AK = "AopW6jDWoGJM_HjV3zaA1Rr77Tl_pgkK2KnNy5ZK";
    private static final String SK = "o1zzCmG_JalqW2QcGuXI8oec1HSq0KJfflJOZp0D";
    private static final String BUCKET = "qiniu";


    @Autowired
    ScenicMapper scenicMapper;
    @Autowired
    ScenicAccountMapper scenicAccountMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    ShopAccountMapper shopAccountMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AccountRoleMapper accountRoleMapper;

    /**
     * 获取所有的景区信息
     * @return
     */
    @Override
    public List<Scenic> findAllScenic() {
        return scenicMapper.selectByExample(new ScenicExample());
    }

    /**
     * 获取上传凭证
     * @return
     */
    @Override
    public String getUploadToken() {
        Auth auth = Auth.create(AK,SK);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(BUCKET, null, expireSeconds, putPolicy);

        return upToken;
    }

    /**
     * 新增scenic
     * @param scenic
     * @param scenicAccount
     */
    @Override
    public void saveScenic(Scenic scenic, ScenicAccount scenicAccount) {
        //补全 并向数据库中插入scenicAccount数据
        Date time = new Date();
        scenicAccount.setCreateTime(time);
        scenicAccount.setUpdateTime(time);
        scenicAccount.setAccountPhoto(scenic.getPhoto());

        scenicAccountMapper.insert(scenicAccount);

        //补全 并向数据库插入scenic数据
        scenic.setCreateTime(time);
        scenic.setUpdateTime(time);
        scenic.setAccountid(scenicAccount.getId());

        scenicMapper.insert(scenic);
    }

    /**
     * 根据景区id查询具体对象
     * @param scenicId
     * @return
     */
    @Override
    public Scenic findScenicById(int scenicId) {
        Scenic scenic = scenicMapper.selectByPrimaryKey(scenicId);
        return scenic;
    }

    /**
     * 查询所有的售票点信息并对应其负责人信息
     * @return
     */
    @Override
    public List<Shop> findAllShop() {
        List<Shop> shopList = shopMapper.selectByExample(new ShopExample());
        for(Shop shop : shopList){
            ShopAccount shopAccount = shopAccountMapper.selectByPrimaryKey(shop.getShopAccountid());
            shop.setShopAccount(shopAccount);
        }
        return shopList;
    }


    /**
     * 向数据库添加shopAccount 和 shop
     * @param shopAccount
     * @param shop
     */
    @Override
    public void createShop(ShopAccount shopAccount, Shop shop) {
        shopAccountMapper.insert(shopAccount);
        shop.setShopAccountid(shopAccount.getId());
        shopMapper.insert(shop);
    }

    /**
     * 查询所有的旅游局用户
     * @return
     */
    @Override
    public List<Account> findAllTravelAccount() {
        List<Account> accountList = accountMapper.selectByExample(new AccountExample());
        for(Account account : accountList){
            //根据account的id查询所有对应的角色
            List<Role> roleList = roleMapper.findAllRoleByAccountId(account.getId());
            account.setRoleList(roleList);
        }
        return accountList;
    }

    /**
     * 根据售票点的id来获取对象
     * @param shopId
     * @return
     */
    @Override
    public Shop findShopByShopId(Integer shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    /**
     * 根据售票点手机号来获取对象
     * @param shopPhone
     * @return
     */
    @Override
    public Shop findShopByPhone(String shopPhone) {
        ShopAccountExample shopAccountExample = new ShopAccountExample();
        shopAccountExample.createCriteria().andAccountEqualTo(shopPhone);
        List<ShopAccount> shopAccountList = shopAccountMapper.selectByExample(shopAccountExample);
        Shop shop = shopMapper.findShopByPhone(shopPhone);
        shop.setShopAccount(shopAccountList.get(0));
        return shop;
    }

    /**
     * 新增旅游局用户
     * @param account
     * @return
     */
    @Override
    public int insertAccount(Account account) {
        Date date = new Date();
        account.setCreateTime(date);
        account.setUpdateTime(date);
        accountMapper.insert(account);
        return account.getId();
    }

    /**
     * 新增旅游局用户与权限关系
     * @param role
     * @param accountId
     */
    @Override
    public void insertRoleRelationship(String role, int accountId) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(role);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        int roleId = roleList.get(0).getId();

        AccountRoleKey accountRoleKey = new AccountRoleKey();
        accountRoleKey.setAccountId(accountId);
        accountRoleKey.setRoleId(roleId);
        accountRoleMapper.insert(accountRoleKey);
    }

    /**
     * 查询所有的角色
     * @return
     */
    @Override
    public List<Role> findAllRole() {
        return roleMapper.selectByExample(new RoleExample());
    }
}
