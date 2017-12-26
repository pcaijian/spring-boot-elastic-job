package com.elastic.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import com.elastic.util.MyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 需要继承BasePojo,因为需要使用到更新和添加时间
 */
public abstract class BaseService<T> {

	@Autowired
	private MyMapper<T> myMapper;

	// #################################################

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * 
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(int id) {
		return this.myMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<T> queryAll() {
		return this.myMapper.select(null);
	}

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 * 
	 * @param record
	 * @return
	 */
	public T selectOne(T record) {
		return this.myMapper.selectOne(record);
	}

	/**
	 * 根据条件查询数据列表
	 * 
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return this.myMapper.select(record);
	}
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 * @param record
	 * @return
	 */
	public int selectCount(T record){
		return myMapper.selectCount(record);
	}

	/**
	 * 分页查询
	 * 
	 * @param page 页
	 * @param rows 行
	 * @param record 查询条件
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record) {
		// 设置分页条件
		PageHelper.startPage(page, rows);
		List<T> list = this.queryListByWhere(record);
		return new PageInfo<T>(list);
	}

	// ###################################################

	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * 
	 * @param record
	 * @return
	 */
	public Integer insert(T record) {
		return this.myMapper.insert(record);
	}

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * 
	 * @param record
	 * @return
	 */
	public Integer insertSelective(T record) {
		return this.myMapper.insertSelective(record);
	}

	/**
	 * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 *  测试未通过,不建议使用
	 * @return
	 */
	public int insertList(List<T> recordList) {
		return this.myMapper.insertList(recordList);
	}

	/**
	 * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
	 *   测试未通过,不建议使用
	 * @param record
	 * @return
	 */
	public int insertUseGeneratedKeys(T record) {
		return this.myMapper.insertUseGeneratedKeys(record);
	}
	

	// ####################################################

	/**
	 * 修改数据，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer update(T record) {
		return this.myMapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改数据，使用不为null的字段，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer updateSelective(T record) {
		return this.myMapper.updateByPrimaryKeySelective(record);
	}
	

	// ###################################################

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByPrimaryKey(Integer id) {
		return this.myMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param clazz 对象名称
	 * @param property 属性
	 * @param values 属性值列表
	 * @return
	 */
	public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return this.myMapper.deleteByExample(example);
	}

	/**
	 * 根据条件做删除(根据实体属性作为条件进行删除，查询条件使用等号)
	 * 
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record) {
		return this.myMapper.delete(record);
	}

}
