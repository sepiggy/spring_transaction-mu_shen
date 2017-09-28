package cn.sepiggy.imooc.demo1.service;

import cn.sepiggy.imooc.demo1.dao.AccountDao;

/**
 * 转账案例的业务层实现类
 */
public class AccountServiceImpl implements AccountService {

    // 注入转账的 DAO 的类
    private AccountDao accountDao;

    /**
     * 当没有使用事务时, 若出账和入账操作的代码产生异常, 程序会在异常处停止, 这就导致剩下的操作不能进行.
     * 因此, 需要借助 "事务" 来避免这种错误发生, 使其要么全部成功, 要么全部失败.
     * @param out   转出账号
     * @param in    转入账号
     * @param money 转账金额
     */
    @Override
    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        int i = 1 / 0;
        accountDao.inMoney(in, money);
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
