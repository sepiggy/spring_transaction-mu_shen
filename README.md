# Spring 事务管理

## 1. 事务回顾
### 1.1 什么是事务?
事务指的是逻辑上的一组操作, 这组操作要么全部成功, 要么全部失败.

### 1.2 事务的特性:
`原子性, 一致性, 隔离性, 持久性`
- 原子性: 事务是一个不可分割的工作单位, 事务中的操作要么都发生, 要么都不发生.
- 一致性: 事务操作前后数据的完整性必须保持一致.
- 隔离性: 多个用户并发访问数据库时, 一个用户的事务不能被其他用户的事务所干扰, 多个并发事务之间数据要相互隔离 (通过设置事务的隔离级别来解决这个问题).
- 持久性: 一个事务一旦被提交, 它对数据库中数据的改变就是永久性的, 即使数据库发生故障也不应该对其有任何影响.

## 2. Spring事务管理的一组API
### 2.1. spring事务管理高层抽象主要包括3个接口
#### 2.1.1 PlatformTransactionManager 事务管理器
Spring 为不同的持久层框架提供了不同的 PlatformTransactionManager 接口实现:

事务 | 说明
--------- | ----------
org.springframework.jdbc.datasource.DataSourceTransactionManager | 使用 Spring JDBC 或 Mybatis 进行持久化数据时使用
org.springframework.orm.hibernate3.HibernateTransactionManager | 使用 Hibernate 3.0 版本进行持久化数据时使用
org.springframework.orm.jpa.JpaTransactionManager | 使用 JPA 进行持久化数据时使用
org.springframework.jdo.JdoTransactionManager | 当持久化机制是 JDO 时使用
org.springframework.transaction.jta.JtaTransactionManager | 使用一个 JTA 实现来管理事务, 在一个事务跨越多个资源时必须使用

#### 2.1.2 TransactionDefinition 事务定义信息(隔离, 传播, 超时, 只读)
##### 2.1.2.1 如果不考虑事务的隔离性, 会引发安全问题如下: `脏读, 不可重复读, 幻读`
- 脏读: 一个事务读取了另一个事务改写但还未提交的数据, 如果这些数据被回滚, 则读到的数据是无效的.
- 不可重复读: 在同一个事务中, 多次读取同一数据返回的结果有所不同.
- 幻读: 一个事务读取了几行记录后, 另一个事务插入一些记录, 幻读就发生了. 再后来的查询中, 第一个事务就会发现有些原来没有的记录.

##### 2.1.2.2 事务的隔离级别 (四种)
隔离级别 | 含义
------- | ------
DEFAULT | 使用后端数据库默认的隔离级别(Spring中的选择项)
READ_UNCOMMITED | 允许你读取还未提交的改变了的数据. 可能导致脏读, 幻读, 不可重复读
READ_COMMITTED | 允许在并发事务已经提交后读取. 可防止脏读, 但幻读和不可重复读仍可发生
REPEATABLE_READ | 对相同字段的多次读取是一致的, 除非数据被事务本身改变. 可防止脏读, 不可重复读, 但幻读仍然可能发生
SERIALIZABLE | 完全服从 ACID 的隔离级别, 确保不发生脏读, 幻读, 不可重复读. 这在所有的隔离级别中是最慢的, 它是典型的通过完全锁定在事务中涉及的数据表来完成的.

`注:`
`Mysql 默认采用的是 REPEATABLE_READ 隔离级别`
`Oracle 默认采用的是 READ_COMMITTED 隔离级别`

##### 2.1.2.3 什么是事务的传播行为?
事务的传播行为: 解决业务层方法之间的相互调用的问题.

##### 2.1.2.4 事务的传播行为 (七种)
事务传播行为类型 | 说明
-------------- | ------------------
PROPAGATION_REQUIRED | 支持当前事务, 如果不存在, 就新建一个
PROPAGATION_SUPPORTS | 支持当前事务, 如果不存在, 就不使用事务
PROPAGATION_MANDATORY | 支持当前事务, 如果不存在, 抛出异常
PROPAGATION_REQUIRES_NEW | 如果有事务存在, 挂起当前事务, 创建一个新的事务
PROPAGATION_NOT_SUPPORTED | 以非事务方式运行, 如果有事务存在, 挂起当前事务
PROPAGATION_NEVER | 以非事务方式运行, 如果有事务存在, 抛出异常
PROPAGATION_NESTED | 如果当前事务存在, 则嵌套事务执行

##### 2.1.3. TransactionStatus 事务具体运行状态

## Spring的编程式事务管理
## Spring的声明式事务管理