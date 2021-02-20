package github.gx.middlewarestudy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: MiddlewareStudy
 * @description: 抢红包工具实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-20 12:20
 **/
public class RedPacketUtil {

    /**
     * 二倍均值法计划每个红包可得量
     * @param totalAmount 总金额 单位：分
     * @param totalPeopleNum 总人数
     * @return 红包金额数组，我认为可以直观的模拟出 越往后越多的结果
     */
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {
        List<Integer> amountList = new ArrayList<>();
        // 排除不合法的参数
        if(totalAmount<=0||totalAmount<=0) {
            return amountList;
        }
        // 按照认输分配金额
        Integer restAmount = totalAmount;
        //记录剩余的总人数-初始化时即为指定的总人数
        Integer restPeopleNum = totalPeopleNum;
        //定义产生随机数的实例对象
        Random random = new Random();
        //不断循环遍历、迭代更新地产生随机金额，直到N-1>0
        for (int i = 0; i < totalPeopleNum - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开-amount即为产生的
            //随机金额R-单位为分
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            //更新剩余的总金额M=M-R
            restAmount -= amount;
            //更新剩余的总人数N=N-1
            restPeopleNum--;
            //将产生的随机金额添加进列表List中
            amountList.add(amount);
        }
        //循环完毕，剩余的金额即为最后一个随机金额，也需要将其添加进列表中
        amountList.add(restAmount);
        //将最终产生的随机金额列表返回
        return amountList;
    }
}
