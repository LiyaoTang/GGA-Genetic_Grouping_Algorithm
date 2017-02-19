package com.lm.algorithms.rule.localsearch;

import com.lm.algorithms.rule.IRule;
import com.lm.domain.Entity;

/**
 * @Description 机器选择工件加工的调度规则

 * @author:lm

 * @time:2013-11-6 下午05:40:06

 */
public interface ILocalSearchRule extends IRule {

    public double calPrio(Entity e); //输入参数 应该是编码类型
}
